package com.example.samaritanpokemonapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CapturedPokemon.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CapturedPokemonDAO capturedPokemonDAO();
}
