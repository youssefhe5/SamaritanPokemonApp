package com.example.samaritanpokemonapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    TextView textViewDialogTitle;
    TextView textViewNickname;
    TextView textViewCapturedDate;
    TextView textViewCapturedLevel;

    EditText editTextNickname;
    EditText editTextCapturedDate;
    EditText editTextCapturedLevel;

    ImageView imageViewPokemonDetailsPic;

    Button buttonCapturePokemon;
    Button buttonDialogCapture;

    CardView cardViewCaptureInformation;

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
        textViewNickname = view.findViewById(R.id.textViewNickname);
        textViewCapturedDate = view.findViewById(R.id.textViewCapturedDate);
        textViewCapturedLevel = view.findViewById(R.id.textViewCapturedLevel);

        imageViewPokemonDetailsPic = view.findViewById(R.id.imageViewPokemonDetailsPic);

        buttonCapturePokemon = view.findViewById(R.id.buttonCapturePokemon);

        cardViewCaptureInformation = view.findViewById(R.id.cardViewCaptureInformation);

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

        AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "captured-pokemon-database").build();
        CapturedPokemonDAO capturedPokemonDAO = db.capturedPokemonDAO();
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                for (CapturedPokemon p : capturedPokemonDAO.getAll()){
                    if (p.name.equals(pokemon.getName())){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cardViewCaptureInformation.setVisibility(View.VISIBLE);
                                textViewNickname.setText("Nickname: " + p.nickName);
                                SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
                                textViewCapturedDate.setText("Captured on: " + format.format(Date.valueOf(p.capturedDate)));
                                textViewCapturedLevel.setText("Captured Level: " + p.capturedLevel);
                                buttonCapturePokemon.setVisibility(View.GONE);
                                buttonCapturePokemon.setEnabled(false);
                            }
                        });
                        break;
                    }
                }
            }
        });

        service.shutdown();

        buttonCapturePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_capture, null);

                textViewDialogTitle = dialogView.findViewById(R.id.textViewDialogTitle);
                editTextNickname = dialogView.findViewById(R.id.editTextNickname);
                editTextCapturedDate = dialogView.findViewById(R.id.editTextCapturedDate);
                editTextCapturedLevel = dialogView.findViewById(R.id.editTextCapturedLevel);

                buttonDialogCapture = dialogView.findViewById(R.id.buttonDialogCapture);
                textViewDialogTitle.setText("Capturing " + HomePageFragment.startWithUppercase(pokemon.getName()));

                builder.setView(dialogView);

                AlertDialog dialog = builder.create();
                DatePicker datePicker = new DatePicker(getActivity());
                editTextCapturedDate.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());

                AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "captured-pokemon-database").build();
                CapturedPokemon capturedPokemon = new CapturedPokemon(pokemon.getName(), editTextNickname.getText().toString(), editTextCapturedDate.getText().toString(), editTextCapturedLevel.getText().toString(), pokemon.getPicture());
                CapturedPokemonDAO capturedPokemonDAO = db.capturedPokemonDAO();

                buttonDialogCapture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editTextCapturedDate.getText().toString().isEmpty() || editTextCapturedLevel.getText().toString().isEmpty()){
                            Toast.makeText(getActivity(), "Fill in both Captured Date and Captured Level", Toast.LENGTH_SHORT).show();
                        } else {
                            //TODO: Save pokemon locally
                            ExecutorService service = Executors.newSingleThreadExecutor();
                            service.execute(new Runnable() {
                                @Override
                                public void run() {
                                    capturedPokemonDAO.insert(capturedPokemon);
                                }
                            });
                            service.shutdown();
                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();


            }
        });



        return view;
    }
}