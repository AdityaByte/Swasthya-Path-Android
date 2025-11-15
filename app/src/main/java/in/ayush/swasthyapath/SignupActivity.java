package in.ayush.swasthyapath;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import in.ayush.swasthyapath.component.CustomInputField;
import in.ayush.swasthyapath.dto.SignupDTO;
import in.ayush.swasthyapath.network.ApiClient;
import in.ayush.swasthyapath.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    // IF: Input Field.
    private CustomInputField nameIF, emailIF, phoneNumberIF, dobIF, heightIF, weightIF, passwordIF, genderIF;
    private MaterialButton signupButton;
    private ApiService apiService;
    private TextView loginRedirectBtn;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        apiService = ApiClient.getApiService(this);

        nameIF = findViewById(R.id.name_input);
        emailIF = findViewById(R.id.email_input);
        phoneNumberIF = findViewById(R.id.phone_input);
        dobIF = findViewById(R.id.dob_input);
        heightIF = findViewById(R.id.height_input);
        weightIF = findViewById(R.id.weight_input);
        passwordIF = findViewById(R.id.password_input);
        signupButton = findViewById(R.id.signup_btn);
        genderIF = findViewById(R.id.gender_input);

        loginRedirectBtn = findViewById(R.id.redirect_login_btn);

        loginRedirectBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        dobIF.setEndIconClickListener(value -> {
            showDOBPickerDialog(dobIF);
        });

        signupButton.setOnClickListener(v -> {
            // Here we have to check all the fields are valid or not.
            String nameTxt = nameIF.getText().toString().trim();
            String emailTxt = emailIF.getText().toString().trim();
            String phoneTxt = phoneNumberIF.getText().toString().trim();
            String dobTxt = dobIF.getText().toString().trim();
            Double heightTxt;
            Double weightTxt;
            try {
                heightTxt = Double.parseDouble(heightIF.getText().toString().trim());
            } catch (Exception ex) {
                heightTxt = 0d;
            }

            try {
                weightTxt = Double.parseDouble(weightIF.getText().toString().trim());
            } catch (Exception ex) {
                weightTxt = 0d;
            }

            String passwordTxt = passwordIF.getText().toString().trim();
            String genderTxt = genderIF.getText().toString();

            if (
                nameTxt.isEmpty() ||
                emailTxt.isEmpty() ||
                phoneTxt.isEmpty() ||
                dobTxt.isEmpty() ||
                heightTxt <= 0 ||
                weightTxt < 0 ||
                passwordTxt.isEmpty() ||
                genderTxt.isEmpty() )
            {
                Toast.makeText(this, "Field's cannot be empty, Please fill out all fields", Toast.LENGTH_LONG).show();
                return;
            } else if (passwordTxt.length() < 6) {
                Toast.makeText(this, "Password length should be greater than 6 characters", Toast.LENGTH_SHORT).show();
            }

            // If all the fields are valid we have to make a dto and send the data to the backend.
            SignupDTO signupDTO = SignupDTO
                    .builder()
                    .name(nameTxt)
                    .email(emailTxt)
                    .gender(genderTxt)
                    .dob(dobTxt)
                    .height(heightTxt)
                    .weight(weightTxt)
                    .password(passwordTxt)
                    .build();

            apiService.signup(signupDTO).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Here we have to return show the message and forward the user to the OTP page.
                        Toast.makeText(SignupActivity.this, response.body().get("response"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
                        intent.putExtra("email", emailTxt);
                        startActivity(intent);
                    } else {
                        if (response.body().get("response") != null) {
                            Toast.makeText(SignupActivity.this, response.body().get("response"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "Failed to do signup HTTP: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable throwable) {
                    Log.d("SIGNUP", "Failed to do signup: " + throwable.getMessage(), throwable);
                    Toast.makeText(SignupActivity.this, "Network error: " + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void showDOBPickerDialog(CustomInputField customInputField) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker datePickerView, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String date = String.format(Locale.US, "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    customInputField.getEditText().setText(date);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }
}
