package com.example.samaritanpokemonapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.io.Serializable;

@Database(entities = {CapturedPokemon.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase implements Serializable {
    public abstract CapturedPokemonDAO capturedPokemonDAO();
}
