package in.ayush.swasthyapath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import in.ayush.swasthyapath.adapter.patient.AssessmentPagerAdapter;
import in.ayush.swasthyapath.dto.AssessmentDTO;
import in.ayush.swasthyapath.network.ApiClient;
import in.ayush.swasthyapath.network.ApiService;
import in.ayush.swasthyapath.ui.patient.assessment.AssessmentViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Map;

public class AssessmentActivity extends AppCompatActivity {

    private AssessmentViewModel assessmentViewModel;
    private ViewPager2 assessmentPager;
    private SharedPreferences sharedPreferences;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        assessmentPager = findViewById(R.id.assessmentPager);

        AssessmentPagerAdapter adapter = new AssessmentPagerAdapter(this);
        assessmentPager.setAdapter(adapter);

        assessmentPager.setUserInputEnabled(false);

        // Initializing the assessment viewmodel.
        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        // SharedPreferences.
        sharedPreferences = getSharedPreferences("swasthya_path_db", MODE_PRIVATE);

        // Api Service.
        apiService = ApiClient.getApiService(this);
    }

    public void goToNextPage() {
        int current = assessmentPager.getCurrentItem();
        if (current < 3) {
            assessmentPager.setCurrentItem(current + 1);
        } else {
            submitAssessment();
        }
    }

    private void submitAssessment() {
        // Here we have to make a network call.
        String accessToken = sharedPreferences.getString("access_token", null);

        if (accessToken == null) {
            Toast.makeText(this, "Session expired, please login again.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        AssessmentDTO assessmentDTO = assessmentViewModel.getAssessment();

        String bearerToken = String.format("Bearer %s", accessToken);

        apiService.assessment(bearerToken, assessmentDTO).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // In that case I have to move to the Patient Activity.
                    Toast.makeText(AssessmentActivity.this, response.body().get("response"), Toast.LENGTH_SHORT).show();

                    // If the response is successful we have to move forward to the patient activity.
                    // Changing the assessment status to true.
                    startActivity(new Intent(AssessmentActivity.this, PatientActivity.class));
                    finish();
                } else {
                    if (response.body() != null && response.body().get("response") != null) {
                        Toast.makeText(AssessmentActivity.this, response.body().get("response"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AssessmentActivity.this, "Failed to do assessment, Try again later! HTTP: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable throwable) {
                Toast.makeText(AssessmentActivity.this, "Network error: " + throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
