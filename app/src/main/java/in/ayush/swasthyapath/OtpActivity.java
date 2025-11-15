package in.ayush.swasthyapath;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import in.ayush.swasthyapath.dto.OtpDTO;
import in.ayush.swasthyapath.network.ApiClient;
import in.ayush.swasthyapath.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Map;

public class OtpActivity extends AppCompatActivity {

    private EditText otp1,otp2,otp3,otp4,otp5,otp6;
    private MaterialButton verifyOtpBtn;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        apiService = ApiClient.getApiService(this);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);
        verifyOtpBtn = findViewById(R.id.verify_btn);
        String email = getIntent().getStringExtra("email");

        verifyOtpBtn.setOnClickListener(view -> {
            String otp = String.format(
                    "%s%s%s%s%s%s",
                    getOtpText(otp1),
                    getOtpText(otp2),
                    getOtpText(otp3),
                    getOtpText(otp4),
                    getOtpText(otp5),
                    getOtpText(otp6));

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

                    } else {
                        if (response.body().get("response") != null) {
                            Toast.makeText(OtpActivity.this, response.body().get("response"), Toast.LENGTH_SHORT).show();
                            // Now we have to redirect the user to the login page.
                            startActivity(new Intent(OtpActivity.this, LoginActivity.class));
                            finish();
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
