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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
//glej reqisterActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hides actionBar and sets activity to fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        final EditText etName = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bButton = (Button) findViewById(R.id.bButton);
        final TextView tvReg = (TextView) findViewById(R.id.tvReg);

        tvReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registriraj = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registriraj);
            }
        });

        bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String username = etName.getText().toString();
            final String password = etPassword.getText().toString();
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String Response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(Response);
                        boolean success = jsonResponse.getBoolean("dela");
                        if(success){
                            Toast toast1 = Toast.makeText(getApplicationContext(), "Response!", Toast.LENGTH_SHORT);
                            toast1.show();
                            String name = jsonResponse.getString("username");
                            String email = jsonResponse.getString("email");
                            Intent intent = new Intent(LoginActivity.this, MainnActivity.class);
                            intent.putExtra("username", name);
                            intent.putExtra("email", email);
                            LoginActivity.this.startActivity(intent);
                        }
                        else{
                            Toast toast1 = Toast.makeText(getApplicationContext(), "Vnesli ste napačno up. ime ali geslo", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                    }
                    catch (Exception e) {
                        Toast toast1 = Toast.makeText(getApplicationContext(), "Exception?!", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                }
            };
            loginrequest log = new loginrequest(username, password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(log);

            }
        });
    }
}
