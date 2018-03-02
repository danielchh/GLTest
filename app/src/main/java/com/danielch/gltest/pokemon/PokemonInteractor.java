package com.danielch.gltest.pokemon;

import com.danielch.gltest.R;
import com.danielch.gltest.base.BaseInteractor;
import com.danielch.gltest.db.DBUtils;
import com.danielch.gltest.models.PokeListPojo;
import com.danielch.gltest.models.PokemonPojo;
import com.danielch.gltest.other.Utils;
import com.danielch.gltest.rest.APIClient;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PokemonInteractor extends BaseInteractor<IPokemonPresenter> implements IPokemonInteractor {

    private static final int POKE_LIMIT = 20;
    private int offset;

    private Disposable disposable;

    PokemonInteractor(IPokemonPresenter presenter) {
        setPresenter(presenter);
    }

    @Override
    public void fetchPokemonsFromDB() {
        ArrayList<PokemonPojo> items = DBUtils.getAllPokemons(context());
        offset = items.get(items.size() - 1).getNumber();
        presenter.onPokemonsFetched(items);
    }

    @Override
    public void fetchPokemonsFromServer() {
        if (Utils.isNetworkAvailable()) {
            disposable = APIClient.getClient()
                    .getPokemons(POKE_LIMIT, offset)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<PokeListPojo>() {
                        @Override
                        public void accept(PokeListPojo pokeListPojo) throws Exception {
                            ArrayList<PokemonPojo> responseItems = (ArrayList<PokemonPojo>) pokeListPojo.getResults();
                            presenter.onPokemonsFetched(responseItems);
                            offset += 20;
                            DBUtils.putPokemonsIntoDB(context(), responseItems);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            presenter.onPokemonsFetched(null);
                            showMessage(R.string.err_unexpected);
                        }
                    });
        } else {
            presenter.onPokemonsFetched(null);
            showMessage(R.string.err_connect_to_the_internet);
        }
    }

    @Override
    public void reloadPokemons() {
        if (Utils.isNetworkAvailable()) {
            presenter.clearContent();
            DBUtils.dropDB(context());
            offset = 0;
            fetchPokemonsFromServer();
        } else {
            hideLoading();
            showMessage(R.string.err_connect_to_the_internet);
        }
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
