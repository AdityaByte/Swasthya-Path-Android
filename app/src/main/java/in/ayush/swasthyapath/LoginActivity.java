package in.ayush.swasthyapath;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import in.ayush.swasthyapath.component.CustomInputField;
import in.ayush.swasthyapath.network.ApiClient;
import in.ayush.swasthyapath.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private CustomInputField dropdownInput, emailInput, passInput;
    private MaterialButton loginBtn;
    private TextView redirectSignupBtn;
    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing the variables.
        dropdownInput = findViewById(R.id.drop_down_input);
        emailInput = findViewById(R.id.email_input);
        passInput = findViewById(R.id.pass_input);
        loginBtn = findViewById(R.id.login_btn);
        redirectSignupBtn = findViewById(R.id.redirect_signup_txt);

        apiService = ApiClient.getApiService(this);

        // Initializing shared preferences.
        sharedPreferences = getSharedPreferences("swasthya_path_db", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        redirectSignupBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignupActivity.class));
        });

        // Here we have to do some things.
        loginBtn.setOnClickListener(view -> {

            // Texts
            String dropdownText = dropdownInput.getText().toString().trim();
            String emailText = emailInput.getText().toString().trim();
            String passText = passInput.getText().toString().trim();

            if ( dropdownText.isEmpty() || emailText.isEmpty() || passText.isEmpty() ) {
                Toast.makeText(this, "Field's cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            // checking for dropdown selection.
            if (dropdownInput.getEditText().getTag() == null) {
                Toast.makeText(this, "Please select a valid role!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Now we need to do something like performing the http request to the backend.
            Map<String, String> params = new HashMap<>();
            params.put("userType", dropdownText);
            params.put("email", emailText);
            params.put("password", passText);
            apiService.login(params).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Here we need to make this.
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Once the login is done we have to save the access_token and refresh_token locally.
                        editor.putString("access_token", response.body().get("accessToken"));
                        editor.putString("refresh_token", response.body().get("refreshToken"));
                        editor.putString("role", dropdownText);
                        editor.commit(); // this is crucial.

                        // Here we have to check one thing ok.
                        if (dropdownText.equals("PATIENT")) {
                            boolean assessmentDone = Boolean.parseBoolean(response.body().get("assessment"));
                            // I have to put the assessment to the sharedPreferences.
                            editor.putBoolean("assessment", assessmentDone);
                            if (assessmentDone) {
                                navigateToRoleActivity(dropdownText);
                            } else {
                                // Here we have to forward that to the other page.
                                navigateToRoleActivity("ASSESSMENT");
                            }
                        } else {
                            navigateToRoleActivity(dropdownText);
                        }

                    } else {
                        if (response.body() != null && response.body().containsKey("response")) {
                            Toast.makeText(LoginActivity.this, response.body().get("response"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed: HTTP " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable throwable) {
                    Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void navigateToRoleActivity(String role) {
        Intent intent;
        switch (role) {
            case "PATIENT":
                intent = new Intent(LoginActivity.this, PatientActivity.class);
                break;
            case "ASSESSMENT":
                intent = new Intent(LoginActivity.this, AssessmentActivity.class);
                break;
            default:
                Toast.makeText(this, "Unknown role!", Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(intent);
        finish();
    }
}
