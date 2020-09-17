package nhl.hd.tv.helper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://nhlhd.tv";
    private Retrofit mRetrofit;

    private NetworkService() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }

}
