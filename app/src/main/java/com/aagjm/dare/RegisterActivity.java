package com.aagjm.dare;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_register);
        //referenca na gumbe v xml
        final EditText Geslo = (EditText) findViewById(R.id.etPassword);
        final EditText Priimek = (EditText) findViewById(R.id.etUserName);
        final EditText Ime = (EditText) findViewById(R.id.etName);
        final Button gumb = (Button) findViewById(R.id.etRegister);

        //ob pritisku na gumb..
        gumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //preberi podatke na vhodu in jih shrani v sprem.
                final String aIme = Ime.getText().toString();
                final String aGeslo = Geslo.getText().toString();
                final String aPriimek = Priimek.getText().toString();
                //aktiviran ko dobimo respond s strani serverja

                Response.Listener<String> response = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //z Jsonom odpakiramo stvari ki smo jih zapakirali v skripti
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean sucess = jsonResponse.getBoolean("sucess");
                            // ob uspešni registraciji se odpre LoginActivity
                            if(sucess){
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else{
                                Toast toast = Toast.makeText(getApplicationContext(), "Nepravilno up. ime ali geslo", Toast.LENGTH_SHORT);
                                toast.show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //naredimo request
                RegisterRequest request = new RegisterRequest(aIme,aPriimek,aGeslo,/*hard incoded mac*/"5343:4324",0,response);
                //omogoča da Volley naredi request z razredom ki smo ga napisali...
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(request);

            }
        });
    }
}
