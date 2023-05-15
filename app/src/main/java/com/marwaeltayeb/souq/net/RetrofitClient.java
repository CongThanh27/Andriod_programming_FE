package com.marwaeltayeb.souq.net;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = LOCALHOST;
    private static RetrofitClient mInstance;
    //private final Retrofit retrofit;
    private static Retrofit retrofit;
    public static Retrofit getRetrofitUser() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create((gson)))
                    .build();
        }
        return retrofit;
    }

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

}
