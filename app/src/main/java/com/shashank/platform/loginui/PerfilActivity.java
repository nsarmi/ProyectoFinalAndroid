package com.shashank.platform.loginui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;

import Clases.Persona;

public class PerfilActivity extends AppCompatActivity {

    Persona currentUsr = new Persona();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsonPersona = getIntent().getExtras().getString("UrsActualDTOjson");
        currentUsr = new Gson().fromJson(jsonPersona, Persona.class);
        setContentView(R.layout.activity_perfil);

        ImageView btnIaAReconocer = findViewById(R.id.btnIaAReconocer);

        btnIaAReconocer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PerfilActivity.this, BuscarPersona.class);
                i.putExtra("UrsActualDTOjson", new Gson().toJson(currentUsr));
                startActivity(i);
            }
        });

    }
}
