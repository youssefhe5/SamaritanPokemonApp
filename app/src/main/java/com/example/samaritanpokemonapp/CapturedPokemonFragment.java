package com.example.samaritanpokemonapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CapturedPokemonFragment extends Fragment {

    private static final String CAPTURED_POKEMON_DAO = "CAPTURED_POKEMON_DAO";

    private CapturedPokemonDAO capturedPokemonDAO;

    ListView listView;

    CapturedPokemonAdapter adapter;

    public CapturedPokemonFragment() {
        // Required empty public constructor
    }

    public static CapturedPokemonFragment newInstance(CapturedPokemonDAO capturedPokemonDAO) {
        CapturedPokemonFragment fragment = new CapturedPokemonFragment();
        Bundle args = new Bundle();
        args.putSerializable(CAPTURED_POKEMON_DAO, capturedPokemonDAO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            capturedPokemonDAO = (CapturedPokemonDAO) getArguments().getSerializable(CAPTURED_POKEMON_DAO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_captured_pokemon, container, false);

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                List<CapturedPokemon> capturedPokemons = capturedPokemonDAO.getAll();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView = view.findViewById(R.id.listViewCapturedPokemon);
                        adapter = new CapturedPokemonAdapter(capturedPokemons);
                        listView.setAdapter(adapter);
                    }
                });

            }
        });





        return view;
    }

    public class CapturedPokemonAdapter extends BaseAdapter {

        List<CapturedPokemon> capturedPokemons;

        TextView textViewCapturedPokemonNickname;
        TextView textViewCapturedPokemonDate;
        TextView textViewCapturedPokemonLevel;

        ImageView imageViewCapturedPokemonPicture;

        public CapturedPokemonAdapter(List<CapturedPokemon> capturedPokemons){
            this.capturedPokemons = capturedPokemons;
        }


        @Override
        public int getCount() {
            return capturedPokemons.size();
        }

        @Override
        public Object getItem(int i) {
            return capturedPokemons.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.captured_pokemon_items, parent, false);


            SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");

            CapturedPokemon capturedPokemon = (CapturedPokemon) getItem(i);

            textViewCapturedPokemonNickname = view.findViewById(R.id.textViewCapturedPokemonNickname);
            textViewCapturedPokemonDate = view.findViewById(R.id.textViewCapturedPokemonDate);
            textViewCapturedPokemonLevel = view.findViewById(R.id.textViewCapturedPokemonLevel);

            imageViewCapturedPokemonPicture = view.findViewById(R.id.imageViewCapturedPokemonPicture);

            textViewCapturedPokemonNickname.setText(capturedPokemon.nickName);
            textViewCapturedPokemonDate.setText(getResources().getString(R.string.captured_on) + " " + format.format(Date.valueOf(capturedPokemon.capturedDate)));
            textViewCapturedPokemonLevel.setText(getResources().getString(R.string.captured_level) + " " + capturedPokemon.capturedLevel);

            imageViewCapturedPokemonPicture.setBackgroundColor(Color.parseColor(capturedPokemon.pokemonBackground));

            Glide.with(view)
                    .load(capturedPokemon.pokemonDetail)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageViewCapturedPokemonPicture);

            return view;
        }
    }

}