package com.danielch.gltest.pokemon.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.danielch.gltest.R;
import com.danielch.gltest.models.PokemonPojo;

import java.util.ArrayList;

public class PokemonRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_DEFAULT = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private static final String BASE_IMAGE_URL = "https://pokeapi.co/media/sprites/pokemon/";

    private Context context;
    private ArrayList<PokemonPojo> items;

    public PokemonRVAdapter(ArrayList<PokemonPojo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_DEFAULT) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false);
            vh = new ViewHolderDefault(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_progressbar, parent, false);
            vh = new ViewHolderLoading(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PokemonPojo item = items.get(position);

        if (holder instanceof ViewHolderDefault) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.drawable.pokemon_unknown);

            ((ViewHolderDefault) holder).txtPokeName.setText(item.getName());
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(BASE_IMAGE_URL + item.getNumber() + ".png")
                    .into(((ViewHolderDefault) holder).ivPokeImage);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_DEFAULT;
    }

    class ViewHolderDefault extends RecyclerView.ViewHolder {

        private ImageView ivPokeImage;
        private TextView txtPokeName;

        ViewHolderDefault(View itemView) {
            super(itemView);
            ivPokeImage = itemView.findViewById(R.id.iv_pokemon);
            txtPokeName = itemView.findViewById(R.id.txt_pokemon);
        }
    }

    class ViewHolderLoading extends RecyclerView.ViewHolder {

        ViewHolderLoading(View itemView) {
            super(itemView);
        }
    }
}
