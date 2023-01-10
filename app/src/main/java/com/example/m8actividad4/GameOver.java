package com.example.m8actividad4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GameOver.this, MainActivity.class));
        finish();
    }
}