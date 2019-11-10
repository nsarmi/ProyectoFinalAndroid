package com.shashank.platform.loginui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import helpers.LogIn;

public class reg2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg2);

        final LogIn logIn = new LogIn();
        Button btnReg = (Button) findViewById(R.id.btnRegisterUsr);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtUsr = (EditText) findViewById(R.id.txtUsr);
                EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
                EditText txtFecNac = (EditText) findViewById(R.id.txtDate);
                EditText txtPass = (EditText) findViewById(R.id.txtPass);

                String usr = txtUsr.getText().toString();
                String email = txtEmail.getText().toString();
                String fecNac = txtFecNac.getText().toString();
                String pass = txtPass.getText().toString();

                logIn.RegistrarUsuario(usr, email, fecNac, pass);

                txtUsr.setText("");
                txtEmail.setText("");
                txtFecNac.setText("");
                txtPass.setText("");
            }
        });
    }
}