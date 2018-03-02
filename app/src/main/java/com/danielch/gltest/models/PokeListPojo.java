package com.danielch.gltest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokeListPojo {

    @SerializedName("results")
    @Expose
    private List<PokemonPojo> results = null;

    public List<PokemonPojo> getResults() {
        return results;
    }
}
