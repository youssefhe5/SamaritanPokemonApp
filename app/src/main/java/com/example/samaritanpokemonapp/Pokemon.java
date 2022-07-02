package com.example.samaritanpokemonapp;

import java.io.Serializable;
import java.util.List;

public class Pokemon implements Serializable {

    public Pokemon(){

    }

    public Pokemon(String picture, String name, int pokedexNumber, List<TypeOfPokemon> type, int weight, double height, int HP, int attack, int defence, int specialAttack, int specialDefense, int speed, String backgroundColor) {
        this.picture = picture;
        this.name = name;
        this.pokedexNumber = pokedexNumber;
        this.type = type;
        this.weight = weight;
        this.height = height;
        this.HP = HP;
        this.attack = attack;
        this.defence = defence;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.backgroundColor = backgroundColor;
    }

    private String picture;
    private String name;
    private int pokedexNumber;
    private List<TypeOfPokemon> type;
    private int weight;
    private double height;
    private int HP;
    private int attack;
    private int defence;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private String backgroundColor;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPokedexNumber() {
        return pokedexNumber;
    }

    public void setPokedexNumber(int pokedexNumber) {
        this.pokedexNumber = pokedexNumber;
    }

    public List<TypeOfPokemon> getType() {
        return type;
    }

    public void setType(List<TypeOfPokemon> type) {
        this.type = type;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
