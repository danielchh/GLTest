package com.danielch.gltest.pokemon;

import com.danielch.gltest.base.IBaseInteractor;

interface IPokemonInteractor extends IBaseInteractor {

    void fetchPokemonsFromDB();

    void fetchPokemonsFromServer();

    void reloadPokemons();

    int getOffset();

    void setOffset(int offset);
}
