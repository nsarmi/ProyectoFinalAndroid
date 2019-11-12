package com.shashank.platform.loginui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import Clases.Persona;

public class DashBoard extends AppCompatActivity {

    Persona currentUsr = new Persona();
    Persona selectedUsr = new Persona();
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsoncurrentUsr = getIntent().getExtras().getString("UrsActualDTOjson");
        String jsonselectedUsr = getIntent().getExtras().getString("UrsSeleccionadoDTOjson");
        currentUsr = new Gson().fromJson(jsoncurrentUsr, Persona.class);
        selectedUsr = new Gson().fromJson(jsonselectedUsr, Persona.class);
        setContentView(R.layout.activity_dash_board);


        android.support.v7.widget.CardView cardAcademico = findViewById(R.id.cardAcademico);
        android.support.v7.widget.CardView cardCultural = findViewById(R.id.cardCultural);
        android.support.v7.widget.CardView cardDeportivo = findViewById(R.id.cardDeportivo);
        android.support.v7.widget.CardView cardEtico = findViewById(R.id.cardEtico);

        i = new Intent(DashBoard.this, ReconocerActivity.class);
        i.putExtra("UrsActualDTOjson", new Gson().toJson(currentUsr));
        i.putExtra("UrsSeleccionadoDTOjson", new Gson().toJson(selectedUsr));

        cardDeportivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("AptitudSelected", 1);
                startActivity(i);
            }
        });
        cardAcademico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("AptitudSelected", 2);
                startActivity(i);
            }
        });
        cardEtico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("AptitudSelected", 3);
                startActivity(i);
            }
        });
        cardCultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("AptitudSelected", 4);
                startActivity(i);
            }
        });
    }
}
