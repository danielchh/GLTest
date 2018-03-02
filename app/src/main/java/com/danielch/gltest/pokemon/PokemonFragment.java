package com.danielch.gltest.pokemon;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielch.gltest.R;
import com.danielch.gltest.base.BaseFragment;
import com.danielch.gltest.models.PokemonPojo;
import com.danielch.gltest.other.Utils;
import com.danielch.gltest.pokemon.recyclerview.PokemonRVAdapter;

import java.util.ArrayList;

public class PokemonFragment extends BaseFragment<IPokemonPresenter> implements IPokemonView {

    private static final String KEY_ITEMS = "key_items";
    private static final String KEY_POSITION = "key_position";
    private static final String KEY_OFFSET = "key_offset";
    private static final String KEY_STARTED_LOAD = "key_started_load";
    private static final String KEY_STARTED_LOAD_MORE = "key_started_load_more";

    private RecyclerView recyclerView;
    private PokemonRVAdapter rvAdapter;
    private LinearLayoutManager llManager;
    private ArrayList<PokemonPojo> items;

    private TextView txtNoItemsToShow;

    private boolean startedLoad;

    private boolean onScrolledDownLoading;
    private boolean startedLoadMore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pokemon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = PokemonPresenter.getInstance(this);
        initUi(view);
        if (savedInstanceState != null) {
            items.addAll(savedInstanceState.<PokemonPojo>getParcelableArrayList(KEY_ITEMS));
            llManager.scrollToPositionWithOffset(savedInstanceState.getInt(KEY_POSITION), 0);
            presenter.setOffset(savedInstanceState.getInt(KEY_OFFSET));
            startedLoad = savedInstanceState.getBoolean(KEY_STARTED_LOAD);
            if (startedLoad){
                presenter.reloadPokemons();
            }
            startedLoadMore = savedInstanceState.getBoolean(KEY_STARTED_LOAD_MORE);
            if (startedLoadMore){
                presenter.getMorePokemons();
            }
        } else {
            presenter.getPokemons();
        }
    }

    @Override
    protected void initUi(View view) {
        txtNoItemsToShow = view.findViewById(R.id.txt_no_items);

        items = new ArrayList<>();
        llManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(llManager);
        rvAdapter = new PokemonRVAdapter(items);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!onScrolledDownLoading && !startedLoadMore) {
                    onScrolledDownLoading = true;
                    int visibleItemCount = llManager.getChildCount();
                    int totalItemCount = llManager.getItemCount();
                    int pastVisibleItems = llManager.findFirstVisibleItemPosition();
                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                        startedLoadMore = true;
                        if (Utils.isNetworkAvailable()) {
                            items.add(null);
                            rvAdapter.notifyItemInserted(items.size() - 1);
                            presenter.getMorePokemons();
                        } else {
                            items.add(null);
                            rvAdapter.notifyItemInserted(items.size() - 1);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (items.size() > 0 && items.get(items.size() - 1) == null) {
                                        items.remove(items.size() - 1);
                                        rvAdapter.notifyItemRemoved(items.size());
                                    }
                                    showMessage(R.string.err_connect_to_the_internet);
                                    startedLoadMore = false;
                                    onScrolledDownLoading = false;
                                }
                            }, 1000);
                        }
                    } else {
                        onScrolledDownLoading = false;
                    }
                }
            }
        });
    }

    private void changeNoItemsVisibility(boolean isVisible) {
        if (isVisible && items.isEmpty()) {
            txtNoItemsToShow.setVisibility(View.VISIBLE);
        } else {
            txtNoItemsToShow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh:
                startedLoad = true;
                presenter.reloadPokemons();
                break;
        }
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPokemonsFetched(ArrayList<PokemonPojo> newItems) {
        onScrolledDownLoading = false;
        startedLoad = false;
        if (newItems != null) {
            changeNoItemsVisibility(false);
            startedLoadMore = false;
            if (items.size() > 0 && items.get(items.size() - 1) == null) {
                items.remove(items.size() - 1);
                rvAdapter.notifyItemRemoved(items.size());
            }
            items.addAll(newItems);
            rvAdapter.notifyDataSetChanged();
        } else {
            changeNoItemsVisibility(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_ITEMS, items);
        outState.putInt(KEY_POSITION, ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition());
        outState.putInt(KEY_OFFSET, presenter.getOffset());
        outState.putBoolean(KEY_STARTED_LOAD, startedLoad);
        outState.putBoolean(KEY_STARTED_LOAD_MORE, startedLoadMore);
        super.onSaveInstanceState(outState);
    }
}
