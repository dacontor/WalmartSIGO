package com.bild.sigo.core;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daniel Contreras on 07-08-17.
 * '
 */

public class ServiceCore {

    private static boolean IS_DEBUG = true;
    public static String baseURL = IS_DEBUG ? "http://www.google.cl" : "http://www.google.cl";

    private static Retrofit restAdapter;
    private static ServiceCore instance;

    //public FixtureMethod fixture;

    public static ServiceCore getInstance() {
        if (instance == null)
            instance = new ServiceCore();
        return instance;
    }

    public static ServiceCore getInstance(String baseUrl) {
        if (instance == null)
            instance = new ServiceCore(baseUrl);
        return instance;
    }

    /**
     * Creates a ServiceCore instance with the default base url
     */
    public ServiceCore() {

        restAdapter = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //fixture = FixtureMethod.getInstance(restAdapter);

    }

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .build();

    /**
     * Creates a ServiceCore instance with the given base url
     *
     * @param baseUrl : the base url to use
     */
    public ServiceCore(String baseUrl) {
        ServiceCore.baseURL = baseUrl;

        restAdapter = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //fixture = FixtureMethod.getInstance(restAdapter);

    }

}
