package in.ayush.swasthyapath;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BuildCompat;
import com.google.android.material.internal.StaticLayoutBuilderConfigurer;
import in.ayush.swasthyapath.network.ApiClient;
import in.ayush.swasthyapath.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            // Here we need to check for the access token.
            // If it then we have to check it is valid otherwise have to refresh the token.
            // Right now haven't doing that.



            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }, 2000);

    }
}
