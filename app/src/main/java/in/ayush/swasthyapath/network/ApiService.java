package in.ayush.swasthyapath.network;

import in.ayush.swasthyapath.dto.OtpDTO;
import in.ayush.swasthyapath.dto.SignupDTO;
import in.ayush.swasthyapath.model.DietResponse;
import in.ayush.swasthyapath.model.PatientDietResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.Map;

public interface ApiService {

    @GET("/api/health")
    Call<Map<String, String>> healthRequest();

    @POST("/api/auth/token")
    Call<Map<String, String>> tokenValidationRequest(@Body Map<String, String> request);

    @POST("/api/auth/login")
    Call<Map<String, String>> login(@Body Map<String, String> request);

    @POST("/api/patient/diet")
    Call<PatientDietResponse> generateDietPlan(
            @Header("Authorization") String accessToken
    );

    @POST("/api/auth/signup/patient")
    Call<Map<String, String>> signup(@Body SignupDTO signupDTO);

    @POST("/api/auth/signup/patient/otp")
    Call<Map<String, String>> otpValidation(@Body OtpDTO otpDTO);



}
