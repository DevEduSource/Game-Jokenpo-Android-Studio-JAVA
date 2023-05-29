package com.lifegames.ajudeogoku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firestore.v1.WriteResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {
    TextView txtStatus;

    private MediaPlayer mp;
    int i = 0;

    int playerWin = 0;
    int playerLoser = 0;
    int playerDraw = 0;
    Boolean checkClicked = false;
    int[] enemys = {R.drawable.vegeta, R.drawable.gohan, R.drawable.goku};
    String[] enemyesname = {"Vegeta", "Gohan", "Goku"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mp = MediaPlayer.create(this, R.raw.dbz_battle_song);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mp.start();

        ImageView btnAtivaSom = findViewById(R.id.btnSomOn);
        btnAtivaSom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.sound_button);

        ImageView btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp2.start();
                Intent i = new Intent(Game.this, Home.class);
                startActivity(i);

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int startColor = getWindow().getStatusBarColor();
            int endColor = ContextCompat.getColor(getApplicationContext(), R.color.game);
            ObjectAnimator.ofArgb(getWindow(), "statusBarColor", startColor, endColor).start();
        }

        ImageView imgChar = findViewById(R.id.imgChar);
        TextView txtInimigo = findViewById(R.id.txtInimigo);

        imgChar.setImageResource(R.drawable.goku);
        txtInimigo.setText("Goku");
    }
    public void changeAvatarNext(View view){
        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.sound_button);
        mp2.start();
        ImageView imgChar = findViewById(R.id.imgChar);
        TextView txtInimigo = findViewById(R.id.txtInimigo);

        imgChar.setImageResource(enemys[i]);
        txtInimigo.setText(enemyesname[i]);
        if (i >= 2){
            i=0;
        } else {
            i++;
        }
    }
    public void selecionouPedra(View view){
        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.sound_button);
        mp2.start();
        if(!checkClicked){
            checkClicked = true;
            this.opcaoSelecionada("pedra");
            txtStatus = findViewById(R.id.txtStatus);
            txtStatus.setText("I'm choosing...");
            ImageView imageResultado = findViewById(R.id.imageResultado);
            imageResultado.setImageResource(R.drawable.padrao);
            ImageView imageResultadoUser = findViewById(R.id.imageResultadoUser);
            imageResultadoUser.setImageResource(R.drawable.pedra);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Cooldown");
            alertDialog.setMessage("Wait for the enemy to choose to select again!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
    public void selecionouPapel(View view){
        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.sound_button);
        mp2.start();
        if(!checkClicked){
            checkClicked = true;
            this.opcaoSelecionada("papel");
            txtStatus = findViewById(R.id.txtStatus);
            txtStatus.setText("I'm choosing...");
            ImageView imageResultado = findViewById(R.id.imageResultado);
            imageResultado.setImageResource(R.drawable.padrao);
            ImageView imageResultadoUser = findViewById(R.id.imageResultadoUser);
            imageResultadoUser.setImageResource(R.drawable.papel);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Cooldown");
            alertDialog.setMessage("Wait for the enemy to choose to select again!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
    public void selecionouTesoura(View view){
        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.sound_button);
        mp2.start();
        if(!checkClicked){
            checkClicked = true;
            this.opcaoSelecionada("tesoura");
            txtStatus = findViewById(R.id.txtStatus);
            txtStatus.setText("I'm choosing...");
            ImageView imageResultado = findViewById(R.id.imageResultado);
            imageResultado.setImageResource(R.drawable.padrao);
            ImageView imageResultadoUser = findViewById(R.id.imageResultadoUser);
            imageResultadoUser.setImageResource(R.drawable.tesoura);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Cooldown");
            alertDialog.setMessage("Wait for the enemy to choose to select again!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
    public void opcaoSelecionada(String opcaoSelecionada){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @SuppressLint("RestrictedApi")
                @Override
                public void run() {
                    ImageView imageResultado = findViewById(R.id.imageResultado);
                    ImageView imgResult = findViewById(R.id.imgResult);

                    int numero = new Random().nextInt(3);
                    String[] opcoes = {"pedra", "papel", "tesoura"};
                    String opcaoApp = opcoes[numero];

                    switch(opcaoApp){
                        case "pedra":
                            imageResultado.setImageResource(R.drawable.pedra);
                            break;
                        case "papel":
                            imageResultado.setImageResource(R.drawable.papel);
                            break;
                        case "tesoura":
                            imageResultado.setImageResource(R.drawable.tesoura);
                            break;
                    }

                    if((opcaoApp == "tesoura" && opcaoSelecionada == "papel") || (opcaoApp == "pedra" && opcaoSelecionada == "tesoura") || (opcaoApp == "papel" && opcaoSelecionada == "pedra")){
                        imgResult.setImageResource(R.drawable.gameover);
                        playerLoser = playerLoser + 1;
                    } else if((opcaoSelecionada == "tesoura" && opcaoApp == "papel") || (opcaoSelecionada == "pedra" && opcaoApp == "tesoura") || (opcaoSelecionada == "papel" && opcaoApp == "pedra")){
                        imgResult.setImageResource(R.drawable.win);
                        playerWin = playerWin + 1;
                    } else {
                        imgResult.setImageResource(R.drawable.draw);
                        playerDraw = playerDraw + 1;
                    }
                        txtStatus = findViewById(R.id.txtStatus);
                        txtStatus.setText("Go Play Again?");
                        checkClicked = false;

                    /*
                    Sistema para salvar score no Firebase, não vi necessidade de implementar visto que
                    só é possivel ganhar "1 vez"
                    String dateStr = "28/05/2023";

                    SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateObj = null;
                    try {
                        dateObj = curFormater.parse(dateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
                    String newDateStr = postFormater.format(dateObj);

                    Map<String, Object> details = new HashMap<>();
                    details.put("Winner", playerWin);
                    details.put("Loser", playerLoser);
                    details.put("Draw", playerDraw);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Score")
                            .document(newDateStr)
                            .collection(getDeviceName())
                            .add(details);

                     */

                    TextView txtScoreWin = findViewById(R.id.scoreWin);
                    String convertScoreWinString = String.valueOf(playerWin);
                    txtScoreWin.setText("You won " + convertScoreWinString + "X");

                    TextView txtScoreLoser = findViewById(R.id.scoreLoser);
                    String convertScoreLoserString = String.valueOf(playerLoser);
                    txtScoreLoser.setText("You lost " + convertScoreLoserString + "X");

                    TextView txtScoreDraw = findViewById(R.id.scoreDraw);
                    String convertScoreDrawString = String.valueOf(playerDraw);
                    txtScoreDraw.setText("You draw " + convertScoreDrawString + "X");

                }
            }, 2000);
    }
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        return manufacturer + " " + model;
    }

    @Override
    public void onBackPressed(){
        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.sound_button);
        mp2.start();
        startActivity(new Intent(this, Home.class));
        finishAffinity();
        return;
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