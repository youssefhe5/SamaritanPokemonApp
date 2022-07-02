package com.example.samaritanpokemonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements HomePageFragment.HomePageListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.contentView, new HomePageFragment()).commit();
    }

    @Override
    public void pokemonSelected(Pokemon pokemon) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contentView, PokemonDetailsFragment.newInstance(pokemon)).commit();
    }
}