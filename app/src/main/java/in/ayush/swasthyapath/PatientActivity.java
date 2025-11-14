package in.ayush.swasthyapath;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import in.ayush.swasthyapath.model.DietResponse;
import in.ayush.swasthyapath.model.PatientDietResponse;
import in.ayush.swasthyapath.network.ApiClient;
import in.ayush.swasthyapath.network.ApiService;
import in.ayush.swasthyapath.ui.patient.HomeFragment;
import in.ayush.swasthyapath.ui.patient.HomeViewModel;
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
    // private TextView navHeaderText;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        // shared homeviewmodel.
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

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

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(value -> drawerLayout.openDrawer(GravityCompat.START));

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }

        navigationView.setNavigationItemSelectedListener((menuItem) -> {
            if (menuItem.getItemId() == R.id.nav_home) {
                replaceFragment(new HomeFragment());
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // Along with that I have to make the request to the backend for fetching out the dietplan.
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }


}
