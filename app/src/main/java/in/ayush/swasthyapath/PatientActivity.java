package in.ayush.swasthyapath;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import in.ayush.swasthyapath.model.PatientDietResponse;
import in.ayush.swasthyapath.network.ApiClient;
import in.ayush.swasthyapath.network.ApiService;
import in.ayush.swasthyapath.ui.patient.home.HomeFragment;
import in.ayush.swasthyapath.ui.patient.home.HomeViewModel;
import in.ayush.swasthyapath.ui.patient.profile.ProfileFragment;
import in.ayush.swasthyapath.ui.patient.profile.ProfileViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private SharedPreferences sharedPreferences;
    private ApiService apiService;
    private HomeViewModel homeViewModel;
    private ProfileViewModel profileViewModel;
    private TextView headerUsername, headerEmail;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        View headerView = navigationView.getHeaderView(0);
        headerUsername = headerView.findViewById(R.id.header_username);
        headerEmail = headerView.findViewById(R.id.header_email);

        // shared Home View Model.
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Here we have to fetch the data.
        sharedPreferences = getSharedPreferences("swasthya_path_db", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("access_token", null);

        // Here we have to do something making the request.
        apiService = ApiClient.getApiService(this);

        String bearerToken = String.format("Bearer %s", accessToken);

        apiService.generateDietPlan(bearerToken).enqueue(new Callback<PatientDietResponse>() {
            @Override
            public void onResponse(Call<PatientDietResponse> call, Response<PatientDietResponse> response) {
                if (response.isSuccessful() && response.body() != null ) {
                    PatientDietResponse patientDietResponse = response.body();
                    homeViewModel.setMeals(patientDietResponse.getHealthResponse().getDayPlan());
                    headerUsername.setText(patientDietResponse.getPatient().getName());
                    headerEmail.setText(patientDietResponse.getPatient().getEmail());
                    profileViewModel.setPatientData(patientDietResponse.getPatient());
                } else {
                    homeViewModel.setNetworkError();
                    Toast.makeText(PatientActivity.this, "Failed to get diet plan: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PatientDietResponse> call, Throwable throwable) {
                homeViewModel.setNetworkError();
                Toast.makeText(PatientActivity.this, "Network Error: Please check connection." + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        toolbar.setNavigationOnClickListener(value -> drawerLayout.openDrawer(GravityCompat.START));

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }

        navigationView.setNavigationItemSelectedListener((menuItem) -> {
            if (menuItem.getItemId() == R.id.nav_home) {
                replaceFragment(new HomeFragment());
            } else if (menuItem.getItemId() == R.id.nav_profile) {
                replaceFragment(new ProfileFragment());
            } else if (menuItem.getItemId() == R.id.nav_logout) {
                sharedPreferences.edit().clear().apply();
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
