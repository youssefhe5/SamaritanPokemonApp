package com.example.samaritanpokemonapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CapturedPokemonDAO {
    @Query("SELECT * FROM capturedpokemon")
    List<CapturedPokemon> getAll();

    @Insert
    void insert(CapturedPokemon capturedPokemon);
}
