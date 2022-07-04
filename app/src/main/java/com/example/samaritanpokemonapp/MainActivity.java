package com.example.samaritanpokemonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements HomePageFragment.HomePageListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "captured-pokemon-database").fallbackToDestructiveMigration().build();
        getSupportFragmentManager().beginTransaction().add(R.id.contentView, HomePageFragment.newInstance(db)).commit();
    }

    @Override
    public void pokemonSelected(Pokemon pokemon, CapturedPokemonDAO capturedPokemonDAO) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contentView, PokemonDetailsFragment.newInstance(pokemon, capturedPokemonDAO)).commit();
    }

    @Override
    public void openCapturedPokemonFragment(CapturedPokemonDAO capturedPokemonDAO) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contentView, CapturedPokemonFragment.newInstance(capturedPokemonDAO)).commit();
    }
}