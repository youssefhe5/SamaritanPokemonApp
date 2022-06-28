package com.example.samaritanpokemonapp;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class HomePageFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url("https://pokeapi.co/api/v2/pokemon?limit=20&offset=0")
            .build();

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        return view;
    }

    void request(){
        Gson gson = new GsonBuilder().create();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String body = responseBody.string();

                AllPokemon allPokemon = gson.fromJson(body, AllPokemon.class);
                Pokemon pokemon = allPokemon.results.get(0);
                Log.d("TAG", "onResponse: " + pokemon.url);
                Log.d("TAG", "onResponse: " + body);

                Request newRequest = new Request.Builder()
                        .url(pokemon.url).
                        build();

                client.newCall(newRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        ResponseBody responseBody1 = response.body();
                        String body = responseBody1.string();
                        Log.d("TAG", "onResponse: " + body);
                        PokemonDetails pokemonDetails = gson.fromJson(body, PokemonDetails.class);
                        Log.d("TAG", "PokemonDetailsType: " + pokemonDetails.types.get(1).type.getName());
                        Log.d("TAG", "PokemonDetailsPicture: " + pokemonDetails.sprites.other.officialArtwork.getFront_default());
                        Log.d("TAG", "PokemonDetailsTypeStats: " + pokemonDetails.stats.get(5).stat.getName());
                    }
                });
            }
        });
    }
}