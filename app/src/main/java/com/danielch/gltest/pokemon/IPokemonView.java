package com.danielch.gltest.pokemon;

import com.danielch.gltest.base.IBaseView;
import com.danielch.gltest.models.PokemonPojo;

import java.util.ArrayList;

interface IPokemonView extends IBaseView {

    void onPokemonsFetched(ArrayList<PokemonPojo> items);

    void clearContent();
}
