package com.aagjm.dare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hides actionBar and sets activity to fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);

        //Referenca na gumbe v xml
        final EditText Username = (EditText) findViewById(R.id.etUsername);
        final EditText Email = (EditText) findViewById(R.id.etEmail);
        final EditText Password = (EditText) findViewById(R.id.etPassword);
        final EditText Confirm = (EditText) findViewById(R.id.etPasswordConfirm);
        final TextView Logo = (TextView) findViewById(R.id.tvRegister);
        final Button registerButton = (Button) findViewById(R.id.etRegister);

        //Change color of substring in logo
        final SpannableStringBuilder sb = new SpannableStringBuilder("Do you DARE?");
        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.RED);
        sb.setSpan(fcs, 7, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        Logo.setText(sb);

        //Get users MAC
        final String macAddress;
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress = wInfo.getMacAddress();

        //ob pritisku na gumb..
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //preberi podatke na vhodu in jih shrani v sprem.
            final String sUsername = Username.getText().toString();
            final String sEmail = Email.getText().toString();
            final String sPassword = Password.getText().toString();
            final String sConfirm = Confirm.getText().toString();

            if(sPassword.equals(sConfirm)) {
                //aktiviran ko dobimo respond s strani serverja
                Response.Listener<String> response = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try {
                        //z Jsonom odpakiramo stvari ki smo jih zapakirali v skripti
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean sucess = jsonResponse.getBoolean("sucess");
                        // ob uspešni registraciji se odpre LoginActivity
                        if (sucess) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_SHORT);
                            toast.show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }
                };
                //naredimo request
                RegisterRequest request = new RegisterRequest(sUsername, sEmail, sPassword, macAddress, 0, response);
                //omogoča da Volley naredi request z razredom ki smo ga napisali...
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(request);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT);
                toast.show();
            }
            }
        });
    }
}
