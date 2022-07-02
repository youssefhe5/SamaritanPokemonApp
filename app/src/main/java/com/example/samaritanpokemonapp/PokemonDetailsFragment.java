package com.example.samaritanpokemonapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokemonDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokemonDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String POKEMON = "POKEMON";

    private Pokemon pokemon;

    public PokemonDetailsFragment() {
        // Required empty public constructor
    }


    public static PokemonDetailsFragment newInstance(Pokemon pokemon) {
        PokemonDetailsFragment fragment = new PokemonDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(POKEMON, pokemon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokemon = (Pokemon) getArguments().getSerializable(POKEMON);
        }
    }

    TextView textViewPokemonDetailsNumberAndName;
    TextView textViewPokemonDetailsTypes;
    TextView textViewPokemonDetailsWeight;
    TextView textViewPokemonDetailsHeight;
    TextView textViewPokemonDetailsHP;
    TextView textViewPokemonDetailsAttack;
    TextView textViewPokemonDetailsDefence;
    TextView textViewPokemonDetailsSpecialAttack;
    TextView textViewPokemonDetailsSpecialDefense;
    TextView textViewPokemonDetailsSpeed;


    ImageView imageViewPokemonDetailsPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_details, container, false);

        textViewPokemonDetailsNumberAndName = view.findViewById(R.id.textViewPokemonDetailsNumberAndName);
        textViewPokemonDetailsTypes = view.findViewById(R.id.textViewPokemonDetailsTypes);
        textViewPokemonDetailsWeight = view.findViewById(R.id.textViewPokemonDetailsWeight);
        textViewPokemonDetailsHeight = view.findViewById(R.id.textViewPokemonDetailsHeight);
        textViewPokemonDetailsHP = view.findViewById(R.id.textViewPokemonDetailsHP);
        textViewPokemonDetailsAttack = view.findViewById(R.id.textViewPokemonDetailsAttack);
        textViewPokemonDetailsDefence = view.findViewById(R.id.textViewPokemonDetailsDefence);
        textViewPokemonDetailsSpecialAttack = view.findViewById(R.id.textViewPokemonDetailsSpecialAttack);
        textViewPokemonDetailsSpecialDefense = view.findViewById(R.id.textViewPokemonDetailsSpecialDefense);
        textViewPokemonDetailsSpeed = view.findViewById(R.id.textViewPokemonDetailsSpeed);
        imageViewPokemonDetailsPic = view.findViewById(R.id.imageViewPokemonDetailsPic);

        textViewPokemonDetailsNumberAndName.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));
        imageViewPokemonDetailsPic.setBackgroundColor(Color.parseColor(pokemon.getBackgroundColor()));

        Glide.with(view)
                .load(pokemon.getPicture())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageViewPokemonDetailsPic);

        textViewPokemonDetailsNumberAndName.setText("#" + pokemon.getPokedexNumber() + " " + HomePageFragment.startWithUppercase(pokemon.getName()));

        if (pokemon.getType().size() > 1){
            textViewPokemonDetailsTypes.setText("Type(s): " + HomePageFragment.startWithUppercase(pokemon.getType().get(0).type.getName()) + " · " + HomePageFragment.startWithUppercase(pokemon.getType().get(1).type.getName()));
        } else {
            textViewPokemonDetailsTypes.setText("Type(s): " + HomePageFragment.startWithUppercase(pokemon.getType().get(0).type.getName()));
        }

        textViewPokemonDetailsWeight.setText("Weight: " + pokemon.getWeight() + " kg");
        textViewPokemonDetailsHeight.setText("Height: " + pokemon.getHeight() + " m");
        textViewPokemonDetailsHP.setText("HP: " + pokemon.getHP());
        textViewPokemonDetailsAttack.setText("Attack: " + pokemon.getAttack());
        textViewPokemonDetailsDefence.setText("Defence: " + pokemon.getDefence());
        textViewPokemonDetailsSpecialAttack.setText("Special Attack: " + pokemon.getSpecialAttack() );
        textViewPokemonDetailsSpecialDefense.setText("Special Defense: " + pokemon.getSpecialDefense());
        textViewPokemonDetailsSpeed.setText("Speed: " + pokemon.getSpeed());



        return view;
    }
}