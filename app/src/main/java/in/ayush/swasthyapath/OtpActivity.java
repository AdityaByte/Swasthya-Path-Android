package in.ayush.swasthyapath;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import in.ayush.swasthyapath.component.CustomInputField;
import in.ayush.swasthyapath.dto.OtpDTO;
import in.ayush.swasthyapath.network.ApiClient;
import in.ayush.swasthyapath.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Map;

public class OtpActivity extends AppCompatActivity {

    private CustomInputField otpField;
    private MaterialButton verifyOtpBtn;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        apiService = ApiClient.getApiService(this);

        otpField = findViewById(R.id.otp);
        verifyOtpBtn = findViewById(R.id.verify_btn);
        String email = getIntent().getStringExtra("email");

        verifyOtpBtn.setOnClickListener(view -> {

            String otp = otpField.getText().toString().trim();

            if (otp.length() != 6) {
                Toast.makeText(this, "OTP should be of 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (email == null) {
                Toast.makeText(this, "No user found, email not found", Toast.LENGTH_SHORT).show();
                return;
            }

            // Else we have to make the request.
            OtpDTO otpDTO = OtpDTO
                    .builder()
                    .email(email)
                    .otp(otp)
                    .build();

            apiService.otpValidation(otpDTO).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // When the otp is valid we have to redirect it to the login page.
                        Toast.makeText(OtpActivity.this, response.body().get("response"), Toast.LENGTH_SHORT).show();
                        // Now we have to redirect the user to the login page.
                        startActivity(new Intent(OtpActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        if (response.body().get("response") != null) {
                            Toast.makeText(OtpActivity.this, response.body().get("response"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OtpActivity.this, "OTP verification failed, HTTP:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable throwable) {
                    Log.d("OTP", "Network error: " + throwable.getMessage(), throwable);
                    Toast.makeText(OtpActivity.this, "Network Error: " + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    private String getOtpText(EditText view) {
        return view.getText().toString().trim();
    }
}
