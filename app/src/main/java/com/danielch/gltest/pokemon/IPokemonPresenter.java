package com.danielch.gltest.pokemon;

import com.danielch.gltest.base.IBasePresenter;
import com.danielch.gltest.models.PokemonPojo;

import java.util.ArrayList;

interface IPokemonPresenter extends IBasePresenter {

    void getPokemons();

    void getMorePokemons();

    void onPokemonsFetched(ArrayList<PokemonPojo> items);

    void reloadPokemons();

    void clearContent();

    int getOffset();

    void setOffset(int offset);

}
