package com.danielch.gltest.pokemon;

import com.danielch.gltest.base.BasePresenter;
import com.danielch.gltest.db.DBUtils;
import com.danielch.gltest.models.PokemonPojo;

import java.util.ArrayList;

public class PokemonPresenter extends BasePresenter<IPokemonView, IPokemonInteractor> implements IPokemonPresenter {

    private static PokemonPresenter presenter;

    static PokemonPresenter getInstance(IPokemonView view){
        if (presenter == null){
            presenter = new PokemonPresenter(view);
        } else {
            presenter.setView(view);
        }
        return presenter;
    }

    private PokemonPresenter(IPokemonView view) {
        setView(view);
        interactor = new PokemonInteractor(this);
    }

    @Override
    public void getPokemons() {
        showLoading();
        if (DBUtils.isPokemonsInDBAvailable(context())) {
            interactor.fetchPokemonsFromDB();
        } else {
            interactor.fetchPokemonsFromServer();
        }
    }

    @Override
    public void getMorePokemons() {
        interactor.fetchPokemonsFromServer();
    }

    @Override
    public void onPokemonsFetched(ArrayList<PokemonPojo> items) {
        view.onPokemonsFetched(items);
        hideLoading();
    }

    @Override
    public void reloadPokemons() {
        showLoading();
        interactor.reloadPokemons();
    }

    @Override
    public void clearContent() {
        view.clearContent();
    }

    @Override
    public int getOffset() {
        return interactor.getOffset();
    }

    @Override
    public void setOffset(int offset) {
        interactor.setOffset(offset);
    }
}
