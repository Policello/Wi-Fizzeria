package com.example.mgiorda.mypizzeria2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


import static android.R.attr.button;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    int nbreClicksRoyale = 0;
    HashMap<Integer, Integer> hshIdCkicks;
    ArrayList<Integer> tabId;
    ArrayList<Button> tabButton;
    HashMap<Integer, String> hshIdNames;
    int numTable;
    String s;
    TextView t;
    int click;
    Handler mHandler;
    EnvoiCommande envoi;
    private final int STRING_RESPONSE = 1;
    Thread thread;

    private final String TAG = this.getClass().getName();
    Button init;

    String host = "chadok.info";
    int port = 9874;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabId = new ArrayList<Integer>();

        tabId.add(R.id.Napolitaine);
        tabId.add(R.id.Hawai);
        tabId.add(R.id.Montagnarde);
        tabId.add(R.id.PannaCotta);
        tabId.add(R.id.QuatreFromages);
        tabId.add(R.id.Royale);
        tabId.add(R.id.Tiramisu);
        tabId.add(R.id.Raclette);

        // Préparer la rotation de l'écran
        if (savedInstanceState != null) {
            hshIdCkicks = (HashMap<Integer, Integer>) savedInstanceState.getSerializable("hshMaps");
            hshIdNames = (HashMap<Integer, String>) savedInstanceState.getSerializable("hshNames");
            numTable = savedInstanceState.getInt("numTable");
        }
        else {

                // Récupérer le numéro de table
                Intent intent = getIntent();
                numTable = intent.getIntExtra("numTable",numTable);


                //Stocker le nombre de clicks pour chaque bouton
                hshIdCkicks = new HashMap<Integer, Integer>();

                for (int i : tabId) {
                    int nbreClicks = 0;
                    hshIdCkicks.put(i, nbreClicks);
                }

                //Stocker le nom de chaque bouton
                hshIdNames = new HashMap<Integer, String>();

                hshIdNames.put(R.id.Napolitaine, "Napolitaine");
                hshIdNames.put(R.id.Hawai, "Hawai");
                hshIdNames.put(R.id.Montagnarde, "Montagnarde");
                hshIdNames.put(R.id.PannaCotta, "PannaCotta");
                hshIdNames.put(R.id.QuatreFromages, "Quatre Fromages");
                hshIdNames.put(R.id.Royale, "Royale");
                hshIdNames.put(R.id.Tiramisu, "Tiramisu");
                hshIdNames.put(R.id.Raclette, "Raclette");
        }



        // Stocker tous les boutons
        tabButton = new ArrayList<Button>();

        // Afficher le numéro de table
        s = "Numéro de table : "+ numTable;
        t = (TextView) findViewById(R.id.txtNumTable);

        t.setText(s);

        init = (Button) findViewById(R.id.ReInit);
        init.setOnClickListener(this);
        init.setText("Réinitialiser");

        // Donner à chaque bouton son nom, son nombre de clicks et son Listener
        for (int i : tabId) {
            Button button = (Button) findViewById(i);
            button.getBackground().setColorFilter(0xFF009246, PorterDuff.Mode.MULTIPLY);
            //button.setBackgroundColor(Color.argb(255,00,146,70));
            s = hshIdNames.get(button.getId()) + " : " + hshIdCkicks.get(button.getId());
            button.setText(s);
            button.setOnClickListener(this);
            tabButton.add(button);
        }

    }




    public void onClick(View v) {

        if (v.getId() == init.getId()){
            for (Button b : tabButton ) {
                click = 0;
                hshIdCkicks.put(b.getId(), click);
                String s = hshIdNames.get(b.getId()) + " : "+ click;
                b.setText(s);
            }
        }

        for (Button b : tabButton ){
            if(v.getId() == b.getId()) {

                // Mettre à jour le nombre de clicks
                click = hshIdCkicks.get(b.getId()) + 1;
                String s = hshIdNames.get(b.getId()) + " : "+ click;
                b.setText(s);
                hshIdCkicks.put(b.getId(),click);

                //Création de la string à envoyer
                String textEnvoi = numTable < 10 ? "0" + numTable : ""+ numTable;
                textEnvoi += hshIdNames.get(b.getId());

                //Envoi de la string
                /*envoi = new EnvoiCommande(host, port, this);
                envoi.execute(textEnvoi);*/

                Log.d(TAG, textEnvoi);
                (new EnvoiCommande(host, port, this)).execute(textEnvoi);


            }
        }

    }

    // Sauvegarde des données de l'activité
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("hshMaps", hshIdCkicks);
        outState.putSerializable("hshNames", hshIdNames);
        outState.putInt("numTable", numTable);
    }

    /*public Context getContext() {
        return this.getContext();
    }*/
}
