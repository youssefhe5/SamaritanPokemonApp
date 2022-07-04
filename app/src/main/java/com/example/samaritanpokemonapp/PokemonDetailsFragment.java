package com.example.samaritanpokemonapp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class PokemonDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String POKEMON = "POKEMON";
    private static final String CAPTURED_POKEMON_DAO = "CAPTURED_POKEMON_DAO";

    private Pokemon pokemon;
    private CapturedPokemonDAO capturedPokemonDAO;

    public PokemonDetailsFragment() {
        // Required empty public constructor
    }


    public static PokemonDetailsFragment newInstance(Pokemon pokemon, CapturedPokemonDAO capturedPokemonDAO) {
        PokemonDetailsFragment fragment = new PokemonDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(POKEMON, pokemon);
        args.putSerializable(CAPTURED_POKEMON_DAO, capturedPokemonDAO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokemon = (Pokemon) getArguments().getSerializable(POKEMON);
            capturedPokemonDAO = (CapturedPokemonDAO) getArguments().getSerializable(CAPTURED_POKEMON_DAO);
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

        if (pokemon.getPokedexNumber() < 10){
            textViewPokemonDetailsNumberAndName.setText("#00" + pokemon.getPokedexNumber() + " " + HomePageFragment.startWithUppercase(pokemon.getName()));
        } else if (pokemon.getPokedexNumber() < 100){
            textViewPokemonDetailsNumberAndName.setText("#0" + pokemon.getPokedexNumber() + " " + HomePageFragment.startWithUppercase(pokemon.getName()));
        } else {
            textViewPokemonDetailsNumberAndName.setText("#" + pokemon.getPokedexNumber() + " " + HomePageFragment.startWithUppercase(pokemon.getName()));
        }

        if (pokemon.getType().size() > 1){
            textViewPokemonDetailsTypes.setText("Type(s): " + HomePageFragment.startWithUppercase(pokemon.getType().get(0).type.getName()) + " · " + HomePageFragment.startWithUppercase(pokemon.getType().get(1).type.getName()));
        } else {
            textViewPokemonDetailsTypes.setText("Type(s): " + HomePageFragment.startWithUppercase(pokemon.getType().get(0).type.getName()));
        }

        textViewPokemonDetailsWeight.setText(getResources().getString(R.string.weight) + " " + pokemon.getWeight() + " kg");
        textViewPokemonDetailsHeight.setText(getResources().getString(R.string.height) + " " + pokemon.getHeight() + " m");
        textViewPokemonDetailsHP.setText(getResources().getString(R.string.hp) + " " + pokemon.getHP());
        textViewPokemonDetailsAttack.setText(getResources().getString(R.string.attack) + " " + pokemon.getAttack());
        textViewPokemonDetailsDefence.setText(getResources().getString(R.string.defence) + " " + pokemon.getDefence());
        textViewPokemonDetailsSpecialAttack.setText(getResources().getString(R.string.special_attack) + " " + pokemon.getSpecialAttack() );
        textViewPokemonDetailsSpecialDefense.setText(getResources().getString(R.string.special_defense) + " " + pokemon.getSpecialDefense());
        textViewPokemonDetailsSpeed.setText(getResources().getString(R.string.speed) + " " + pokemon.getSpeed());

        checkCaptureInformation(capturedPokemonDAO);


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
                textViewDialogTitle.setText(getResources().getString(R.string.capturing) + " " + HomePageFragment.startWithUppercase(pokemon.getName()));

                builder.setView(dialogView);

                AlertDialog dialog = builder.create();
                DatePicker datePicker = new DatePicker(getActivity());
                editTextCapturedDate.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());

                buttonDialogCapture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editTextCapturedDate.getText().toString().isEmpty() || editTextCapturedLevel.getText().toString().isEmpty()){
                            Toast.makeText(getActivity(), getResources().getString(R.string.toast_error_message), Toast.LENGTH_SHORT).show();
                        } else {
                            CapturedPokemon capturedPokemon = new CapturedPokemon(pokemon.getName(), editTextNickname.getText().toString(), editTextCapturedDate.getText().toString(), editTextCapturedLevel.getText().toString(), pokemon.getPicture(), pokemon.getBackgroundColor());
                            ExecutorService service = Executors.newSingleThreadExecutor();
                            service.execute(new Runnable() {
                                @Override
                                public void run() {
                                    capturedPokemonDAO.insert(capturedPokemon);
                                    checkCaptureInformation(capturedPokemonDAO);
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

    void checkCaptureInformation(CapturedPokemonDAO capturedPokemonDAO){
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                for (CapturedPokemon p : capturedPokemonDAO.getAll()){
                    if (p.name.equalsIgnoreCase(pokemon.getName())){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cardViewCaptureInformation.setVisibility(View.VISIBLE);
                                textViewNickname.setText(getResources().getString(R.string.nickname) + " " + p.nickName);
                                SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
                                textViewCapturedDate.setText(getResources().getString(R.string.captured_on) + " " + format.format(Date.valueOf(p.capturedDate)));
                                textViewCapturedLevel.setText(getResources().getString(R.string.captured_level) + " " + p.capturedLevel);
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
    }
}