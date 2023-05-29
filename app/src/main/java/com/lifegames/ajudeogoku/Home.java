package com.lifegames.ajudeogoku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.ktx.Firebase;

public class Home extends AppCompatActivity {
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int startColor = getWindow().getStatusBarColor();
            int endColor = ContextCompat.getColor(getApplicationContext(), R.color.home);
            ObjectAnimator.ofArgb(getWindow(), "statusBarColor", startColor, endColor).start();
        }

        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.sound_button);

        mp = MediaPlayer.create(this, R.raw.dbz_sound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mp.start();

        ImageView btnExitGame = findViewById(R.id.btnExit);
        btnExitGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        ImageView btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp2.start();
                Intent i = new Intent(Home.this, Game.class);
                startActivity(i);
                finish();
            }
        });

        ImageView btnAtivaSom = findViewById(R.id.btnSomOn);
        btnAtivaSom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp2.start();
                boolean checkPressed = false;
                btnAtivaSom.setSelected(!btnAtivaSom.isPressed());

                if (btnAtivaSom.isPressed()) {
                    btnAtivaSom.setImageResource(R.drawable.somoff);
                    btnAtivaSom.setSelected(btnAtivaSom.isPressed());
                } else {
                    btnAtivaSom.setImageResource(R.drawable.somon);
                }

                if (mp.isPlaying()) {
                    mp.pause();
                } else {
                    mp.start();
                }
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mp.start();
    }
}