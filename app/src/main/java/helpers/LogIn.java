package helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.shashank.platform.loginui.MainActivity;

import org.json.JSONObject;

import javax.xml.transform.Result;

import helpers.ServiceConsume.LoginTask;

public class LogIn {
    public LogIn() {

    }

    public Boolean Ingreso = false;

    public boolean ValidarCredenciales(Context c, @NonNull String email, @NonNull String pass) {
        try {
            if (email.trim().length() > 0 && pass.trim().length() > 0) {

                String url = String.format("http://10.0.2.2/unilab/home/ValidarAcceso?email=%1$s&pass=%2$s", email, pass);
                //String url = String.format("http://10.0.2.2/unilab/home/ValidarAcceso?email=%1$s&pass=%2$s", "test@test.com", "test");
                RequestQueue requestQueue = Volley.newRequestQueue(c);

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
                                Ingreso = t != "";
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("NSWS", "onError WS: " + error);
                            }
                        }
                );

                requestQueue.add(jsonObjectRequest);
                if (Ingreso)
                    return true;
                else
                    return false;
            }
        } catch (Exception e) {
            Log.e("ErrorSN", "error en consultar las credenciales" + e.getMessage());
            return false;
        }
        return false;
    }

    public String RegistrarUsuario(String usr, String email, String fecNac, String pass) {
        try {
            LoginTask t = new LoginTask();
            String url = String.format("http://10.0.2.2/unilab/home/RegistrarUsuario?nombre=%1$s&email=%2$s&date=%3$s&pass=%4$s", usr, email, fecNac, pass);
            t.execute(url);
            return "Ok";
        } catch (Exception e) {
            Log.e("ErrorSN", "Se jodio en la llamada de la URL");
            e.printStackTrace();
            return "Error";
        }
    }
}