package in.ayush.swasthyapath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import in.ayush.swasthyapath.network.ApiClient;
import in.ayush.swasthyapath.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ApiService apiService = ApiClient.getApiService(this);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("swasthya_path_db", MODE_PRIVATE);
            String accessToken = sharedPreferences.getString("access_token", null);

            if (accessToken == null) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return;
            }

            // Else if there is a token we have to validate the token at the server.
            Map<String, String> params = new HashMap<>();
            params.put("access_token", accessToken);

            apiService.tokenValidationRequest(params).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    Intent intent;
                    if (response.isSuccessful()) {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    } else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable throwable) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            });

        }, 2000);
    }
}