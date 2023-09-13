package com.example.listadetareas_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

public class Splash extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        editText = findViewById(R.id.editText);

        final Intent i = new Intent(Splash.this, MainActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, 1000);

        //Ubicación
        String ubicacion = "font/ConcertOne-Regular.ttf";
        Typeface tf = Typeface.createFromAsset(Splash.this.getAssets(), ubicacion);

        editText.setTypeface(tf);

    }
}