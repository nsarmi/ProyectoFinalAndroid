package com.shashank.platform.loginui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapters.ReconocimientoAdapter;
import Clases.Aptutud;
import Clases.Persona;
import Clases.Reconocimiento;
import helpers.ServiceConsume.Generic;

public class ReconocerActivity extends AppCompatActivity {

    Persona currentUsr = new Persona();
    Persona selectedUsr = new Persona();
    Aptutud selectedAptitud = new Aptutud();

    private String _personaIdDestino;
    private String _personaIdOrigen;
    private int _motivoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String jsoncurrentUsr = getIntent().getExtras().getString("UrsActualDTOjson");
        String jsonselectedUsr = getIntent().getExtras().getString("UrsDestinoDTOjson");
        String jsonselectedAptitud = getIntent().getExtras().getString("AptitudSelected");
        currentUsr = new Gson().fromJson(jsoncurrentUsr, Persona.class);
        selectedUsr = new Gson().fromJson(jsonselectedUsr, Persona.class);
        selectedAptitud = new Gson().fromJson(jsonselectedAptitud, Aptutud.class);
        _personaIdDestino = selectedUsr.nombre;
        _personaIdOrigen = currentUsr.nombre;
        _motivoId = selectedAptitud.id;

        setContentView(R.layout.activity_reconocer);

        Button btnRegistrarReconocimiento = findViewById(R.id.btnRegistrarReconocimiento);
        TextView txtNombreUsrDestino = (TextView) findViewById(R.id.txtUsrDestReco);
        TextView txtAptitud = (TextView) findViewById(R.id.txtAptitudReco);
        txtAptitud.setText(selectedAptitud.nombre);
        txtNombreUsrDestino.setText(selectedUsr.nombre);

        btnRegistrarReconocimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                EditText txtdescripcion = (EditText) findViewById(R.id.txtDescReco);
                String descripcion = txtdescripcion.getText().toString();

                if (descripcion.trim().length() > 0) {

                    String urlBase = getResources().getString(R.string.service_url);
                    String url = String.format("%1$s/home/SetNewreconocimiento?idaptitud=%2$s&idUsrdestino=%3$s&idUsrOrigen=%4$s&descripcion=%5$s", urlBase, selectedAptitud.id, selectedUsr.id, currentUsr.id, new Generic().encodeValue(descripcion));
                    RequestQueue requestQueue = Volley.newRequestQueue(ReconocerActivity.this);

                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    String jsonResponse = response.toString();
                                    Type ListPerType = new TypeToken<ArrayList<Reconocimiento>>() {
                                    }.getType();
                                    List<Reconocimiento> recos = new Gson().fromJson(jsonResponse, ListPerType);

                                    Intent i = new Intent(ReconocerActivity.this, PerfilActivity.class);
                                    i.putExtra("UrsActualDTOjson", new Gson().toJson(currentUsr));

                                    startActivity(i);
                                    finish();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("NSERROR", "onResponse WS: " + error);
                                }
                            }
                    );

                    requestQueue.add(jsonArrayRequest);

                } else {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}
