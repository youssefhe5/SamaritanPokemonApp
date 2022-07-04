package com.example.samaritanpokemonapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.io.Serializable;
import java.util.List;

@Dao
public interface CapturedPokemonDAO extends Serializable {
    @Query("SELECT * FROM capturedpokemon")
    List<CapturedPokemon> getAll();

    @Insert
    void insert(CapturedPokemon capturedPokemon);
}
