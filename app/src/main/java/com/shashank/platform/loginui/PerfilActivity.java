package com.shashank.platform.loginui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapters.ReconocimientoAdapter;
import Clases.Persona;
import Clases.Reconocimiento;

public class PerfilActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Persona currentUsr = new Persona();

    private List<Reconocimiento> mlstReconocimientos = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ReconocimientoAdapter mAdapter;

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


        mRecyclerView = findViewById(R.id.reciclerEstrellasRecividas);


        String urlBase = getResources().getString(R.string.service_url);
        String url = String.format("%1$s/home/GetAllreconocimientos?idUsuario=%2$s", urlBase, currentUsr.id);
        RequestQueue requestQueue = Volley.newRequestQueue(PerfilActivity.this);

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
                        mlstReconocimientos = recos;

                        mAdapter = new ReconocimientoAdapter(mlstReconocimientos);
//                        mAdapter.setClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                int pos = mRecyclerView.indexOfChild(view);
//                                //Toast.makeText(PerfilActivity.this, "elemento clikeado : " + mlstReconocimientos.get(pos).id, Toast.LENGTH_LONG).show();
//                                Intent i = new Intent(PerfilActivity.this, DashBoard.class);
//                                i.putExtra("IdUsuarioDestino", mlstReconocimientos.get(pos).id);
//
//                                startActivity(i);
//                                Log.e("NSOK", "Funciona esta jodaaaaa perro");
//                            }
//                        });
                        mRecyclerView.setAdapter(mAdapter);

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
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
