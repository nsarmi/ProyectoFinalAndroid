package com.shashank.platform.loginui;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    int count = 0;

    protected Dialog OnDialog(Context c, String msg) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        //builder = builder.setIcon(R.drawable.ic_email_white_24dp);
        builder = builder.setTitle("Error");
        builder = builder.setTitle(msg);
        dialog = builder.create();

        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        Button btnLogIn = (Button) findViewById(R.id.btnLogIn);
        Button btnRegis = (Button) findViewById(R.id.btnRegister);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText email = (EditText) findViewById(R.id.txtLogInUsr);
                EditText pass = (EditText) findViewById(R.id.txtLogInPass);
                if (email.getText().toString().trim().length() > 0 && pass.getText().toString().trim().length() > 0) {
                    String urlBase = getResources().getString(R.string.service_url);
                    String url = String.format("%3$s/home/ValidarAcceso?email=%1$s&pass=%2$s", email.getText().toString(), pass.getText().toString(), urlBase);

                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("NSWS", "onResponse WS: " + response);

                                    Gson gson = new Gson();
                                    JsonElement jsonTree = gson.toJsonTree(response);
                                    JsonObject jsonObject = jsonTree.getAsJsonObject();
                                    String t = jsonObject.getAsJsonObject("nameValuePairs").get("Email").toString();

                                    if (t != "") {
                                        Intent i = new Intent(MainActivity.this, DashBoard.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        OnDialog(MainActivity.this, "Datos Errados").show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    OnDialog(MainActivity.this, "Datos Errados").show();
                                    Log.e("NSWS", "onError WS: " + error);
                                }
                            }
                    );

                    requestQueue.add(jsonObjectRequest);

                } else {
                    OnDialog(MainActivity.this, "Ingese Datos").show();
                }
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, reg2.class);
                startActivity(i);
                final TextView textView = (TextView) findViewById(R.id.text);
            }
        });

        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Buena noche");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Buen Dia");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Buena noche");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Buen Dia");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });
    }
}
