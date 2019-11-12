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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import Clases.Persona;

public class ReconocerActivity extends AppCompatActivity {

    Persona currentUsr = new Persona();
    Persona selectedUsr = new Persona();

    private String _personaIdDestino;
    private String _personaIdOrigen;
    private String _motivoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String jsoncurrentUsr = getIntent().getExtras().getString("UrsActualDTOjson");
//        String jsonselectedUsr = getIntent().getExtras().getString("UrsSeleccionadoDTOjson");
//        String Aptitudselected = getIntent().getExtras().getString("AptitudSelected");
//        currentUsr = new Gson().fromJson(jsoncurrentUsr, Persona.class);
//        selectedUsr = new Gson().fromJson(jsonselectedUsr, Persona.class);
        setContentView(R.layout.activity_reconocer);
//
//        _personaIdDestino = selectedUsr.nombre;
//        _personaIdOrigen = currentUsr.nombre;
//        _motivoId = Aptitudselected;
        Button btnRegistrarReconocimiento = findViewById(R.id.btnRegistrarReconocimiento);

        btnRegistrarReconocimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //TODO:nico:colocar el llamado a la api y probar el

                EditText email = (EditText) findViewById(R.id.txtLogInUsr);
                if (email.getText().toString().trim().length() > 0) {
                    String urlBase = getResources().getString(R.string.service_url);
                    String url = String.format("%3$s/home/ValidarAcceso?email=%1$s&pass=%2$s", email.getText().toString(), urlBase);

                    RequestQueue requestQueue = Volley.newRequestQueue(ReconocerActivity.this);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {

                                        String jsonResponse = response.toString();
                                        Persona persona = new Gson().fromJson(jsonResponse, Persona.class);
                                        if (persona != null && persona.id > 0) {
                                            Intent i = new Intent(ReconocerActivity.this, DashBoard.class);
                                            i.putExtra("UrsActualDTOjson", new Gson().toJson(persona));
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    } catch (Exception e) {
                                        Log.e("NSWS", "onResponse WS: " + e.getMessage());
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                    );

                    requestQueue.add(jsonObjectRequest);

                } else {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}
