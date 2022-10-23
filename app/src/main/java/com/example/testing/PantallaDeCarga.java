package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PantallaDeCarga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga);

        final int Duracion = 3000; //Tiempo

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Esto se ejecutara los segundos
                Intent intent = new Intent( PantallaDeCarga.this, MainActivity.class);
                startActivity(intent);
                //Nos dirige al mainactivity

            }
        },Duracion);
    }



}