package com.danielch.gltest.rest;

import com.danielch.gltest.models.PokeListPojo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface PokeApiService {

    @GET("pokemon")
    Observable<PokeListPojo> getPokeList(@Query("limit") int limit,
                                         @Query("offset") int offset);

}
