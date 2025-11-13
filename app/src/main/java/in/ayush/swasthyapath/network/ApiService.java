package in.ayush.swasthyapath.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.Map;

public interface ApiService {

    @GET("/api/health")
    Call<Map<String, String>> healthRequest();

    @POST("/api/auth/token")
    Call<Map<String, String>> tokenValidationRequest(@Body Map<String, String> request);

    @POST("/api/auth/login")
    Call<Map<String, String>> login(@Body Map<String, String> request);
}