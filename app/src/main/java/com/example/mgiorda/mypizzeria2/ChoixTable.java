package com.example.mgiorda.mypizzeria2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ChoixTable extends AppCompatActivity implements View.OnClickListener {
    TextView t;
    Button buttonGo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_table);
        t = (TextView) findViewById(R.id.editText);
        t.setText("");
        buttonGo = (Button) findViewById(R.id.button);
        buttonGo.setBackgroundColor(Color.argb(255,00,146,70));
        buttonGo.setOnClickListener(this);
        buttonGo.setText("Commencer commande");
    }

    public void onClick(View v) {
            if (v.getId() == buttonGo.getId()) {

                Pattern pattern = Pattern.compile("^([0-9]{2}|[1-9])$");
                if (pattern.matcher(t.getText()).matches()) {
                    int numTable = Integer.parseInt(t.getText().toString());
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("numTable", numTable);
                    startActivity(intent);
                    //buttonGo.setText("" + numTable);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Le numéro de table est incorrect", Toast.LENGTH_LONG).show();
                }

            }
    }




}
