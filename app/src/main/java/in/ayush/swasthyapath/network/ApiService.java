package in.ayush.swasthyapath.network;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.Map;

public interface ApiService {

    @GET("health")
    Call<Map<String, String>> healthRequest();
}