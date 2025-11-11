package in.ayush.swasthyapath.network;

import android.content.Context;
import in.ayush.swasthyapath.utils.ConfigUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            String backendUrl = ConfigUtil.getBackendUrl(context);
            OkHttpClient client = new OkHttpClient();
            retrofit = new Retrofit.Builder()
                    .baseUrl(backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService(Context context) {
        return getClient(context).create(ApiService.class);
    }
}
