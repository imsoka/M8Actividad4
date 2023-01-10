package com.example.m8actividad4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.jetbrains.annotations.Contract;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        Button btnPlay2 = (Button) findViewById(R.id.btnPlay2);

        //Depende del botón que pulsemos, elegimos una dificultad o otra, (multiplicador de velocidad)
        btnPlay.setOnClickListener(goToPantalla("1"));
        btnPlay2.setOnClickListener(goToPantalla("2"));
    }

    @NonNull
    @Contract(" -> new")
    private View.OnClickListener goToPantalla(String dificultad) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Pantalla.class);
                intent.putExtra("dificultad", dificultad);
                startActivity(intent);
            }
        };
    }
}