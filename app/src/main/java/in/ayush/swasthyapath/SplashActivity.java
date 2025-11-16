package in.ayush.swasthyapath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
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

    private ApiService apiService;
    private Handler handler;
    private Runnable navigationRunnable;
    private Call<Map<String, String>> validationCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash);

        apiService = ApiClient.getApiService(this);

        handler = new Handler(Looper.getMainLooper());
        navigationRunnable = new Runnable() {
            @Override
            public void run() {
                handleNavigation();
            }
        };

        // Start navigation after a delay of 2s.
        handler.postDelayed(navigationRunnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove any pending callbacks to prevent memory leaks
        if (handler != null && navigationRunnable != null) {
            handler.removeCallbacks(navigationRunnable);
        }
        // Cancel ongoing network calls
        if (validationCall != null) {
            validationCall.cancel();
        }
    }

    private void handleNavigation() {
        SharedPreferences sp = getSharedPreferences("swasthya_path_db", MODE_PRIVATE);
        String accessToken = sp.getString("access_token", null);
        String role = sp.getString("role", null);

        if (accessToken == null) {
            navigateToLogin();
        } else {
            // Validate token with server
            Map<String, String> params = new HashMap<>();
            params.put("access_token", accessToken);

            validationCall = apiService.tokenValidationRequest(params);
            validationCall.enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String respText = response.body().get("response");
                        Toast.makeText(SplashActivity.this, respText, Toast.LENGTH_SHORT).show();
                        navigateToRoleActivity(role);
                    } else {
                        Log.e("SPLASH", "Token validation failed with code: " + response.code());
                        navigateToLogin();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    if (!call.isCanceled()) {
                        Log.e("SPLASH", "Network/Request Failure: " + t.getMessage());
                        navigateToLogin();
                    }
                }
            });
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void navigateToRoleActivity(String role) {
        Intent intent;
        switch (role) {
            case "PATIENT":
                intent = new Intent(SplashActivity.this, PatientActivity.class);
                break;
            case "ASSESSMENT":
                intent = new Intent(SplashActivity.this, AssessmentActivity.class);
                break;
            default:
                Toast.makeText(this, "Unknown role!", Toast.LENGTH_SHORT).show();
                return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}