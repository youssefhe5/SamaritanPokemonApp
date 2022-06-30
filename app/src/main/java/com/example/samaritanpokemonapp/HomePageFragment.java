package com.example.samaritanpokemonapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class HomePageFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();

    GridView gridView;

    HomePageGridViewAdapter adapter;

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
        getActivity().setTitle("Home Page");

        gridView = view.findViewById(R.id.gridView);




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
                List<Pokemon> pokemons = new ArrayList<>();
                AllPokemon allPokemon = gson.fromJson(body, AllPokemon.class);

                for (PokemonNameAndURL result : allPokemon.results) {

                    Request request = new Request.Builder()
                            .url(result.url)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                PokemonDetails pokemonDetails = gson.fromJson(response.body().string(), PokemonDetails.class);

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Pokemon pokemon = new Pokemon();
                                        pokemon.setName(pokemonDetails.getName());
                                        pokemon.setPicture(pokemonDetails.sprites.other.officialArtwork.getFront_default());
                                        pokemon.setPokedexNumber(pokemonDetails.getOrder());
                                        pokemon.setWeight(pokemonDetails.getWeight());
                                        pokemon.setHeight(pokemonDetails.getHeight());
                                        pokemon.setType(pokemonDetails.types);

                                        pokemons.add(pokemon);
                                        adapter = new HomePageGridViewAdapter(getActivity(), R.layout.row_grid_items, pokemons);
                                        gridView.setAdapter(adapter);

                                        Log.d("TAG", "onResponse: " + pokemon.getName());

                                    }
                                });

                            }

                        }
                    });

                }
            }
        });
    }

    public class HomePageGridViewAdapter extends BaseAdapter{

        List<Pokemon> pokemons;

        public HomePageGridViewAdapter(Context context, int resource, List<Pokemon> pokemons){
            this.pokemons = pokemons;
        }

        @Override
        public int getCount() {
            return pokemons.size();
        }

        @Override
        public Pokemon getItem(int i) {
            return pokemons.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.row_grid_items, parent, false);
            }

            Pokemon pokemon = getItem(position);

            TextView textViewPokemonNumberAndName = view.findViewById(R.id.textViewPokemonNumberAndName);
            TextView textViewPokemonTypes = view.findViewById(R.id.textViewPokemonTypes);
            ImageView imageViewPokemonPicture = view.findViewById(R.id.imageViewPokemonPicture);

            switch (pokemon.getType().get(0).type.getName()){
                case "rock":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#BBAA66"));
                    break;
                case "ghost":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#6666BA"));
                    break;
                case "steel":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#AAAABB"));
                    break;
                case "water":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#3399FE"));
                    break;
                case "grass":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#76CC55"));
                    break;
                case "psychic":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#FF5599"));
                    break;
                case "ice":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#65CCFF"));
                    break;
                case "dark":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#775444"));
                    break;
                case "fairy":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#EE99EE"));
                    break;
                case "normal":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#AAAA9B"));
                    break;

                case "fighting":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#BA5544"));
                    break;
                case "flying":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#8799FF"));
                    break;
                case "poison":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#AA5599"));
                    break;
                case "ground":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#DDBB54"));
                    break;
                case "bug":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#A9BB22"));
                    break;
                case "fire":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#EB5435"));
                    break;
                case "electric":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#FFCC33"));
                    break;
                case "dragon":
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor("#6666BA"));
                    break;
            }

            Glide.with(view)
                    .load(pokemon.getPicture())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageViewPokemonPicture);
            
            textViewPokemonNumberAndName.setText("#" + pokemon.getPokedexNumber() + " " + startWithUppercase(pokemon.getName()));
            if (pokemon.getType().size() > 1){
                textViewPokemonTypes.setText(startWithUppercase(pokemon.getType().get(0).type.getName()) + " · " + startWithUppercase(pokemon.getType().get(1).type.getName()));
            } else {
                textViewPokemonTypes.setText(startWithUppercase(pokemon.getType().get(0).type.getName()));
            }


            return view;
        }
    }

    StringBuilder startWithUppercase(String word){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word.substring(0,1).toUpperCase());
        stringBuilder.append(word.substring(1));

        return  stringBuilder;
    }
}

