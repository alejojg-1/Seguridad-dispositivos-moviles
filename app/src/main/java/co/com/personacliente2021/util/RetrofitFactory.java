package co.com.personacliente2021.util;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private final OkHttpClient simpleClient = new OkHttpClient.Builder()
            .readTimeout(Parametro.CONNECTION_TIMEOUT_RETROFIT, TimeUnit.SECONDS)
            .connectTimeout(Parametro.CONNECTION_TIMEOUT_RETROFIT, TimeUnit.SECONDS)
            .build();

    private final OkHttpClient tokenClient = new OkHttpClient.Builder().addInterceptor(chain -> {
        Request newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + getToken())
                .build();
        return chain.proceed(newRequest);
    }).readTimeout(Parametro.CONNECTION_TIMEOUT_RETROFIT, TimeUnit.SECONDS)
            .connectTimeout(Parametro.CONNECTION_TIMEOUT_RETROFIT, TimeUnit.SECONDS)
            .build();

    private String getToken() {
        GlobalState globalState = (GlobalState) getContext().getApplicationContext();
        return globalState.getAccessToken();
    }


    private Context context;

    public RetrofitFactory(Context context) {
        this.context = context;
    }




    protected Retrofit getAuthInstance(){
        return new Retrofit.Builder().baseUrl(Parametro.URL_BASE).addConverterFactory(GsonConverterFactory.create()).client(simpleClient).build();
    }

    protected Retrofit getTokenInstance(){
        return new Retrofit.Builder().client(tokenClient)
                .baseUrl(Parametro.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }



    protected Context getContext(){
        return context;
    }



}
