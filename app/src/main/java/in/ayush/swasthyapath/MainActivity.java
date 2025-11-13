package in.ayush.swasthyapath;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import in.ayush.swasthyapath.network.ApiClient;
import in.ayush.swasthyapath.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView2);

//        ApiService apiService = ApiClient.getApiService(this);
//
//        apiService.healthRequest().enqueue(new Callback<Map<String, String>>() {
//            @Override
//            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
//                textView.setText(response.body().get("response"));
//            }
//
//            @Override
//            public void onFailure(Call<Map<String, String>> call, Throwable throwable) {
//                textView.setText(throwable.getMessage());
//            }
//        });
    }
}
