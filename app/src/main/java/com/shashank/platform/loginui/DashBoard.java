package com.shashank.platform.loginui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import Clases.Persona;
import Clases.Aptutud;

public class DashBoard extends AppCompatActivity {

    Persona currentUsr = new Persona();
    Persona selectedUsr = new Persona();
    Aptutud selectedAptitud = new Aptutud();
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsoncurrentUsr = getIntent().getExtras().getString("UrsActualDTOjson");
        String jsonselectedUsr = getIntent().getExtras().getString("UrsDestinoDTOjson");

        currentUsr = new Gson().fromJson(jsoncurrentUsr, Persona.class);
        selectedUsr = new Gson().fromJson(jsonselectedUsr, Persona.class);
        setContentView(R.layout.activity_dash_board);


        android.support.v7.widget.CardView cardAcademico = findViewById(R.id.cardAcademico);
        android.support.v7.widget.CardView cardCultural = findViewById(R.id.cardCultural);
        android.support.v7.widget.CardView cardDeportivo = findViewById(R.id.cardDeportivo);
        android.support.v7.widget.CardView cardEtico = findViewById(R.id.cardEtico);

        i = new Intent(DashBoard.this, ReconocerActivity.class);
        i.putExtra("UrsActualDTOjson", new Gson().toJson(currentUsr));
        i.putExtra("UrsDestinoDTOjson", new Gson().toJson(selectedUsr));

        cardDeportivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAptitud.id=1;
                selectedAptitud.nombre="Deportivo";
                i.putExtra("AptitudSelected", new Gson().toJson(selectedAptitud));
                startActivity(i);
            }
        });
        cardAcademico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAptitud.id=2;
                selectedAptitud.nombre="Academico";
                i.putExtra("AptitudSelected", new Gson().toJson(selectedAptitud));
                startActivity(i);
            }
        });
        cardEtico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAptitud.id=3;
                selectedAptitud.nombre="Etico";
                i.putExtra("AptitudSelected", new Gson().toJson(selectedAptitud));
                startActivity(i);
            }
        });
        cardCultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAptitud.id=4;
                selectedAptitud.nombre="Cultural";
                i.putExtra("AptitudSelected", new Gson().toJson(selectedAptitud));
                startActivity(i);
            }
        });
    }
}
