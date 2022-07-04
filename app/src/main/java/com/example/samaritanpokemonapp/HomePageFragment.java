package com.example.samaritanpokemonapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class HomePageFragment extends Fragment {

    private final static String DB = "db";

    private final OkHttpClient client = new OkHttpClient();

    private AppDatabase db;

    GridView gridView;

    HomePageGridViewAdapter adapter;

    Request request = new Request.Builder()
            .url("https://pokeapi.co/api/v2/pokemon?limit=20&offset=0")
            .build();

    AllPokemon allPokemon;

    List<Pokemon> pokemons = new ArrayList<>();

    boolean userScrolled = false;

    public HomePageFragment() {
        // Required empty public constructor
    }

    public static HomePageFragment newInstance(AppDatabase db) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putSerializable(DB, db);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = (AppDatabase) getArguments().getSerializable(DB);
        request(request);
    }

    ImageView imageViewPokeBall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        imageViewPokeBall = view.findViewById(R.id.imageViewPokeBall);

        gridView = view.findViewById(R.id.gridView);

        adapter = new HomePageGridViewAdapter(getActivity(), R.layout.row_grid_items, pokemons);

        gridView.setAdapter(adapter);

        imageViewPokeBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openCapturedPokemonFragment(db.capturedPokemonDAO());
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CapturedPokemonDAO capturedPokemonDAO = db.capturedPokemonDAO();
                listener.pokemonSelected(pokemons.get(position), capturedPokemonDAO);
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    userScrolled = true;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (userScrolled && firstVisibleItem + visibleItemCount == totalItemCount){
                    if (allPokemon.next != null){
                        Request newRequest = new Request.Builder()
                                .url(allPokemon.next)
                                .build();
                        request(newRequest);
                    }
                    userScrolled = false;
                }
            }
        });



        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof HomePageListener){
            listener = (HomePageListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    HomePageListener listener;

    public interface HomePageListener{
        void pokemonSelected(Pokemon pokemon, CapturedPokemonDAO capturedPokemonDAO);
        void openCapturedPokemonFragment(CapturedPokemonDAO capturedPokemonDao);
    }

    void request(Request request){
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
                allPokemon = gson.fromJson(body, AllPokemon.class);
                Log.d("TAG", "onResponse: " + allPokemon.next);

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
                                        pokemon.setHP(pokemonDetails.stats.get(0).getBase_stat());
                                        pokemon.setAttack(pokemonDetails.stats.get(1).getBase_stat());
                                        pokemon.setDefence(pokemonDetails.stats.get(2).getBase_stat());
                                        pokemon.setSpecialAttack(pokemonDetails.stats.get(3).getBase_stat());
                                        pokemon.setSpecialDefense(pokemonDetails.stats.get(4).getBase_stat());
                                        pokemon.setSpeed(pokemonDetails.stats.get(5).getBase_stat());

                                        pokemons.add(pokemon);

                                        adapter.notifyDataSetChanged();

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

            Comparator<Pokemon> comparator = (pokemon1, pokemon2) -> Integer.compare(pokemon1.getPokedexNumber(), pokemon2.getPokedexNumber());

            Collections.sort(pokemons, comparator);

            TextView textViewPokemonNumberAndName = view.findViewById(R.id.textViewPokemonNumberAndName);
            TextView textViewPokemonTypes = view.findViewById(R.id.textViewPokemonTypes);
            ImageView imageViewPokemonPicture = view.findViewById(R.id.imageViewPokemonPicture);

            //Chooses correct colors
            switch (pokemon.getType().get(0).type.getName()){
                case "rock":
                    pokemon.setBackgroundColor("#BBAA66");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "ghost":
                    pokemon.setBackgroundColor("#6666BA");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "steel":
                    pokemon.setBackgroundColor("#AAAABB");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "water":
                    pokemon.setBackgroundColor("#3399FE");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "grass":
                    pokemon.setBackgroundColor("#76CC55");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "psychic":
                    pokemon.setBackgroundColor("#FF5599");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "ice":
                    pokemon.setBackgroundColor("#65CCFF");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "dark":
                    pokemon.setBackgroundColor("#775444");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "fairy":
                    pokemon.setBackgroundColor("#EE99EE");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "normal":
                    pokemon.setBackgroundColor("#AAAA9B");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "fighting":
                    pokemon.setBackgroundColor("#BA5544");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "flying":
                    pokemon.setBackgroundColor("#8799FF");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "poison":
                    pokemon.setBackgroundColor("#AA5599");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "ground":
                    pokemon.setBackgroundColor("#DDBB54");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "bug":
                    pokemon.setBackgroundColor("#A9BB22");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "fire":
                    pokemon.setBackgroundColor("#EB5435");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "electric":
                    pokemon.setBackgroundColor("#FFCC33");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
                case "dragon":
                    pokemon.setBackgroundColor("#6666BA");
                    imageViewPokemonPicture.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
                    break;
            }
            
            //Display images from url, uses default android image if desired image cannot be displayed
            Glide.with(view)
                    .load(pokemon.getPicture())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageViewPokemonPicture);

            //Display text
            textViewPokemonNumberAndName.setText("#" + pokemon.getPokedexNumber() + " " + startWithUppercase(pokemon.getName()));
            if (pokemon.getType().size() > 1){
                textViewPokemonTypes.setText(startWithUppercase(pokemon.getType().get(0).type.getName()) + " · " + startWithUppercase(pokemon.getType().get(1).type.getName()));
            } else {
                textViewPokemonTypes.setText(startWithUppercase(pokemon.getType().get(0).type.getName()));
            }



            return view;
        }
    }

    //Make words start with an uppercase letter
    public static StringBuilder startWithUppercase(String word){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word.substring(0,1).toUpperCase());
        stringBuilder.append(word.substring(1));

        return  stringBuilder;
    }
}

