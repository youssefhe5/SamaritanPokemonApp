package com.example.samaritanpokemonapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CapturedPokemon {

    public CapturedPokemon() {

    }

    public CapturedPokemon(@NonNull String name, String nickName, String capturedDate, String capturedLevel, String pokemonDetail) {
        this.name = name;
        this.nickName = nickName;
        this.capturedDate = capturedDate;
        this.capturedLevel = capturedLevel;
        this.pokemonDetail = pokemonDetail;
    }

    @PrimaryKey
    @NonNull
    public String name;

    @ColumnInfo(name = "nickname")
    public String nickName;

    @ColumnInfo(name = "captured_date")
    public String capturedDate;

    @ColumnInfo(name = "captured_level")
    public String capturedLevel;

    @ColumnInfo(name = "pokemon_detail")
    public String pokemonDetail;


}
