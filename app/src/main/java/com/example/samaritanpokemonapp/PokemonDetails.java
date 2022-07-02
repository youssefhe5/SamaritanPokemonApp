package com.example.samaritanpokemonapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PokemonDetails {
    String name;
    int order;
    List<TypeOfPokemon> types;
    Sprites sprites;
    int weight;
    double height;
    List<Stats> stats;

    public PokemonDetails() {
        types = new ArrayList<>();
        stats = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public List<TypeOfPokemon> getTypes() {
        return types;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public int getWeight() {
        return (int) Math.round(weight/10.0);
    }

    public double getHeight() {
        return height/10.0;
    }

    public List<Stats> getStats() {
        return stats;
    }
}

class TypeOfPokemon{
    int slot;
    PokemonType type;

    public int getSlot() {
        return slot;
    }

}

class PokemonType{
    String name;
    String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}

class Sprites{
    Other other;
}

class Other{
    @SerializedName("official-artwork")
    OfficialArtwork officialArtwork;
}

class OfficialArtwork{
    String front_default;

    public String getFront_default() {
        return front_default;
    }

}

class Stats{
    int base_stat;
    Stat stat;

    public int getBase_stat() {
        return base_stat;
    }
}

class Stat{
    String name;

    public String getName() {
        return name;
    }
}