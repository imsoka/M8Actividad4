package com.example.m8actividad4;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Pantalla extends AppCompatActivity {
    private Juego juego;
    private Handler handler;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.activity_pantalla);

        //Instanciamos el juego
        Juego juego =  findViewById(R.id.Juego);

        //Escuchamos este observer para definir el tamaño de nuestro juego y la posición del jugador
        ViewTreeObserver observer = juego.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                //Asignamos las propiedades del juego
                juego.width = juego.getWidth();
                juego.height = juego.getHeight();
                //Inicializamos la cesta
                juego.posBasket = new int[2];
                juego.posBasket[0] = Juego.BASKET_SIZE;
                juego.posBasket[1] = Juego.BASKET_SIZE;
                //Seteamos la dificultad
                juego.dificultad = Integer.parseInt(getIntent().getExtras().getString("dificultad"));

                //Inicializamos las frutas
                initFruit(juego, juego.posFruit1);
                initFruit(juego, juego.posFruit2);
                initFruit(juego, juego.posFruit3);
                initFruit(juego, juego.posFruit4);
                initCandy(juego, juego.posCandy1);
                initCandy(juego, juego.posCandy2);
                initCandy(juego, juego.posCandy3);
                initCandy(juego, juego.posCandy4);
            }
        });

        //Renderizamos el juego cada 20 milisegundos
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Actualizamos las frutas
                        juego.posFruit1[1] -= Juego.FRUIT_SPEED * juego.dificultad;
                        juego.posFruit2[1] -= Juego.FRUIT_SPEED * juego.dificultad;
                        juego.posFruit3[1] -= Juego.FRUIT_SPEED * juego.dificultad;
                        juego.posFruit4[1] -= Juego.FRUIT_SPEED * juego.dificultad;
                        juego.posCandy1[1] -= Juego.CANDY_SPEED * juego.dificultad;
                        juego.posCandy2[1] -= Juego.CANDY_SPEED * juego.dificultad;
                        juego.posCandy3[1] -= Juego.CANDY_SPEED * juego.dificultad;
                        juego.posCandy4[1] -= Juego.CANDY_SPEED * juego.dificultad;

                        //Si una fruta o una golosina se va de los limites, la volvemos a inicializar
                        if(juego.posFruit1[1] < 0) {
                            initFruit(juego, juego.posFruit1);
                        }
                        if(juego.posFruit2[1] < 0) {
                            initFruit(juego, juego.posFruit2);
                        }
                        if(juego.posFruit3[1] < 0) {
                            initFruit(juego, juego.posFruit3);
                        }
                        if(juego.posFruit4[1] < 0) {
                            initFruit(juego, juego.posFruit4);
                        }
                        if(juego.posCandy1[1] < 0) {
                            initCandy(juego, juego.posCandy1);
                        }
                        if(juego.posCandy2[1] < 0) {
                            initCandy(juego, juego.posCandy2);
                        }
                        if(juego.posCandy3[1] < 0) {
                            initCandy(juego, juego.posCandy3);
                        }
                        if(juego.posCandy4[1] < 0) {
                            initCandy(juego, juego.posCandy4);
                        }

                        //Renderizar juego
                        juego.invalidate();
                    }
                });
            }
        }, 0, 20);
    }

    //Metodo para inicializar frutas
    private void initFruit(Juego juego, int[] fruit) {
        Random random = new Random();
        fruit[0] = random.nextInt(juego.width - Juego.FRUIT_SIZE) + Juego.FRUIT_SIZE;
        fruit[1] = random.nextInt(10000 - juego.height) + juego.height;
    }

    //Metodo para inicializar caramelos
    private void initCandy(Juego juego, int[] candy) {
        Random random = new Random();
        candy[0] = random.nextInt(juego.width - Juego.CANDY_SIZE) + Juego.CANDY_SIZE;
        candy[1] = random.nextInt(10000 - juego.height) + juego.height;
    }
}