package com.danielch.gltest.rest;

import com.danielch.gltest.models.PokeListPojo;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    private static APIClient apiClient;
    private final PokeApiService pokeService;

    private APIClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        pokeService = retrofit.create(PokeApiService.class);
    }

    public static APIClient getClient() {
        if (apiClient == null) {
            apiClient = new APIClient();
        }
        return apiClient;
    }

    public Observable<PokeListPojo> getPokemons(int limit, int offset) {
        return pokeService.getPokeList(limit, offset);
    }

}
