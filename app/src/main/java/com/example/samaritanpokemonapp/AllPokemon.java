package com.example.samaritanpokemonapp;

import java.util.ArrayList;
import java.util.List;

public class AllPokemon {
    int count;
    String next;
    String previous;
    List<Pokemon> results;

    public AllPokemon() {
        results = new ArrayList<Pokemon>();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(List<Pokemon> results) {
        this.results = results;
    }
}
