package com.danielch.gltest;

import android.os.Bundle;

import com.danielch.gltest.base.BaseActivity;
import com.danielch.gltest.other.Utils;
import com.danielch.gltest.pokemon.PokemonFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.isNetworkAvailable(context());

        // so that fragment is not created two times after orientation change
        if (getSupportFragmentManager().getFragments().isEmpty()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_main, new PokemonFragment())
                    .commit();
        }
    }
}
