package com.aagjm.dare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView etUsername = (TextView) findViewById(R.id.etUserName);
        final TextView tvSmall = (TextView) findViewById(R.id.tvSmall);
        final TextView tvLarge = (TextView) findViewById(R.id.tvLarge);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String message = "Pozdravljen";
        tvLarge.setText(message);
        tvSmall.setText(username);
        etUsername.setText(email);

    }
}
