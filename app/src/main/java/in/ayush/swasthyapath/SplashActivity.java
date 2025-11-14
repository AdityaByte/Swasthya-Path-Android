package in.ayush.swasthyapath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash);

        ApiService apiService = ApiClient.getApiService(this);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("swasthya_path_db", MODE_PRIVATE);
            String accessToken = sharedPreferences.getString("access_token", null);
            String role = sharedPreferences.getString("role", null);

            if (accessToken == null) {
                // startActivity(new Intent(this, PatientActivity.class));
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
                    if (response.isSuccessful() && response.body() != null) {
                        String respText = response.body().get("response");
                        Toast.makeText(SplashActivity.this, respText, Toast.LENGTH_SHORT).show();
                        navigateToRoleActivity(role);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable throwable) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            });

        }, 2000);
    }

    private void navigateToRoleActivity(String role) {
        Intent intent;
        switch (role) {
            case "PATIENT":
                intent = new Intent(SplashActivity.this, PatientActivity.class);
                break;
            default:
                Toast.makeText(this, "Unknown role!", Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(intent);
        finish();
    }
}