package com.example.samaritanpokemonapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CapturedPokemon implements Serializable {

    public CapturedPokemon() {

    }

    public CapturedPokemon(@NonNull String name, String nickName, @NonNull String capturedDate, @NonNull String capturedLevel, String pokemonDetail, String pokemonBackground) {
        this.name = name;
        this.nickName = nickName;
        this.capturedDate = capturedDate;
        this.capturedLevel = capturedLevel;
        this.pokemonDetail = pokemonDetail;
        this.pokemonBackground = pokemonBackground;
    }

    @PrimaryKey
    @NonNull
    public String name;

    @ColumnInfo(name = "nickname")
    public String nickName;

    @NonNull
    @ColumnInfo(name = "captured_date")
    public String capturedDate;

    @NonNull
    @ColumnInfo(name = "captured_level")
    public String capturedLevel;

    @ColumnInfo(name = "pokemon_detail")
    public String pokemonDetail;

    @ColumnInfo(name = "pokemon_background")
    public String pokemonBackground;


}
