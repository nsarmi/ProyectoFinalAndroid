package com.shashank.platform.loginui;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapters.PersonaBusquedaAdapter;
import Clases.Persona;

public class BuscarPersona extends AppCompatActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    Persona currentUsr = new Persona();
    Persona selectedUsr = new Persona();

    private List<Persona> LstPersonasAvailable = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private PersonaBusquedaAdapter personaBusquedaAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String jsonPersona = getIntent().getExtras().getString("UrsActualDTOjson");
        //currentUsr = new Gson().fromJson(jsonPersona, Persona.class);
        setContentView(R.layout.activity_buscar_persona);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recyclerViewPersonas);

        String urlBase = getResources().getString(R.string.service_url);
        String url = String.format("%1$s/home/GetAllUsuarios", urlBase);
        RequestQueue requestQueue = Volley.newRequestQueue(BuscarPersona.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String jsonResponse = response.toString();
                        Type ListPerType = new TypeToken<ArrayList<Persona>>() {
                        }.getType();
                        List<Persona> pers = new Gson().fromJson(jsonResponse, ListPerType);
                        LstPersonasAvailable = pers;

                        personaBusquedaAdapter = new PersonaBusquedaAdapter(LstPersonasAvailable);
                        personaBusquedaAdapter.setClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int pos = mRecyclerView.indexOfChild(view);
                                Toast.makeText(BuscarPersona.this, "elemento clikeado : " + LstPersonasAvailable.get(pos).id, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(BuscarPersona.this, ReconocerActivity.class);
                                i.putExtra("IdUsuarioDestino", LstPersonasAvailable.get(pos).id);

                                startActivity(i);
                                Log.e("NSOK", "Funciona esta jodaaaaa perro");
                            }
                        });
                        mRecyclerView.setAdapter(personaBusquedaAdapter);

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent i = new Intent(BuscarPersona.this, DashBoard.class);
                i.putExtra("UrsActualDTOjson", new Gson().toJson(currentUsr));
                i.putExtra("UrsSeleccionadoDTOjson", new Gson().toJson(selectedUsr));
                startActivity(i);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buscar_persona, menu);
        MenuItem item = menu.findItem(R.id.buscadorPersonas);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);


        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        try {
            //TODO:Nsarmiento:cambiar el texto imput
            final List<Persona> listaFiltradapersonas = filter(LstPersonasAvailable, "nico");

            personaBusquedaAdapter.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = mRecyclerView.indexOfChild(view);
                    Toast.makeText(BuscarPersona.this, "elemento clikeado : " + listaFiltradapersonas.get(pos).id, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(BuscarPersona.this, ReconocerActivity.class);
                    i.putExtra("IdUsuarioDestino", listaFiltradapersonas.get(pos).id);
                    startActivity(i);
                }
            });
            mRecyclerView.setAdapter(personaBusquedaAdapter);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private List<Persona> filter(List<Persona> personas, String busqueda) {
        List<Persona> listaFiltradapersonas = new ArrayList<>();

        try {
            busqueda = busqueda.toLowerCase();
            for (Persona persona : personas) {
                String tmpPersona = persona.nombre.toLowerCase();

                if (tmpPersona.contains(busqueda)) {
                    listaFiltradapersonas.add(persona);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaFiltradapersonas;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
