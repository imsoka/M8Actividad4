package com.example.m8actividad4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class Juego extends View {
    public int width, height, score, dificultad;
    public int[] posBasket, posFruit1, posFruit2, posFruit3, posFruit4, posCandy1, posCandy2, posCandy3, posCandy4;
    private RectF basket, fruit1, fruit2, fruit3, fruit4, candy1, candy2, candy3, candy4;
    public static int BASKET_SIZE = 150;
    public static int FRUIT_SIZE = 75;
    public static int CANDY_SIZE = 90;
    public static int FRUIT_SPEED = 5;
    public static int CANDY_SPEED = 6;

    public Juego(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        score = 0;
        posFruit1 = new int[2];
        posFruit2 = new int[2];
        posFruit3 = new int[2];
        posFruit4 = new int[2];
        posCandy1 = new int[2];
        posCandy2 = new int[2];
        posCandy3 = new int[2];
        posCandy4 = new int[2];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        //Evitar que la cesta salga de los margenes
        if(x > this.width - BASKET_SIZE) x = this.width - BASKET_SIZE;
        if(x < BASKET_SIZE) x = BASKET_SIZE;

        //seteamos la posición de la cesta
        posBasket[0] = x;

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean add = false;

        //Renderizamos el fondo
        renderBackground(canvas);
        //Renderizamos la cesta
        renderBasket(canvas);
        //Renderizamos las frutas
        renderFruits(canvas);
        //Renderizamos los caramelos
        renderCandies(canvas);
        //Renderizamos los puntos
        renderPoints(canvas);

        //Detectamos colisiones y añadimos o restamos puntos, depende de la fruta sumamos diferentes puntos
        if(RectF.intersects(basket, fruit1)) {
            addPoints(posFruit1, 4);
        }
        if(RectF.intersects(basket, fruit2)) {
            addPoints(posFruit2, 2);
        }
        if(RectF.intersects(basket, fruit3)) {
            addPoints(posFruit3, 3);
        }
        if(RectF.intersects(basket, fruit4)) {
            addPoints(posFruit4, 1);
        }
        if(RectF.intersects(basket, candy1)) {
            removePoints(posCandy1, 3);
        }
        if(RectF.intersects(basket, candy2)) {
            removePoints(posCandy2, 1);
        }
        if(RectF.intersects(basket, candy3)) {
            removePoints(posCandy3, 2);
        }
        if(RectF.intersects(basket, candy4)) {
            removePoints(posCandy4, 4);
        }
    }

    //Metodo para pintar el fondo
    private void renderBackground(Canvas canvas) {
        Paint background = new Paint();
        background.setColor(Color.DKGRAY);
        background.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(new Rect(0, 0, width, height), background);
    }

    //Metodo para pintar la cesta
    private void renderBasket(Canvas canvas) {
        Paint background = new Paint();
        Bitmap basketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.basket);

        basket = new RectF(posBasket[0] - BASKET_SIZE, posBasket[1] - BASKET_SIZE, posBasket[0] + BASKET_SIZE, posBasket[1] + BASKET_SIZE);
        canvas.drawBitmap(basketBitmap, null, basket, background);
    }

    //Metodo para crear rectangulo de fruta
    private RectF newFruit(int[] fruit) {
        return new RectF(
                fruit[0] - FRUIT_SIZE,
                fruit[1] - FRUIT_SIZE,
                fruit[0] + FRUIT_SIZE,
                fruit[1] + FRUIT_SIZE
        );
    }

    //Metodo para crear rectangulo de golosina
    private RectF newCandy(int[] candy) {
        return new RectF(
                candy[0] - FRUIT_SIZE,
                candy[1] - FRUIT_SIZE,
                candy[0] + FRUIT_SIZE,
                candy[1] + FRUIT_SIZE
        );
    }

    //Metodo para renderizar una fruta y su bitmap
    private void renderFruit(Canvas canvas, RectF fruit, int resource) {
        Paint background = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
        canvas.drawBitmap(bitmap, null, fruit, background);
    }

    //Renderizamos todas las frutas
    private void renderFruits(Canvas canvas) {
        fruit1 = newFruit(posFruit1);
        renderFruit(canvas, fruit1, R.drawable.apple);
        fruit2 = newFruit(posFruit2);
        renderFruit(canvas, fruit2, R.drawable.banana);
        fruit3 = newFruit(posFruit3);
        renderFruit(canvas, fruit3, R.drawable.kiwi);
        fruit4 = newFruit(posFruit4);
        renderFruit(canvas, fruit4, R.drawable.watermelon);
    }

    //Renderizamos todas las golosinas
    private void renderCandies(Canvas canvas) {
        candy1 = newCandy(posCandy1);
        renderCandy(canvas, candy1, R.drawable.candy1);
        candy2 = newCandy(posCandy2);
        renderCandy(canvas, candy2, R.drawable.candy2);
        candy3 = newCandy(posCandy3);
        renderCandy(canvas, candy3, R.drawable.candy3);
        candy4 = newCandy(posCandy4);
        renderCandy(canvas, candy4, R.drawable.candy4);
    }

    //Metodo para renderizar una golosina y su bitmap
    private void renderCandy(Canvas canvas, RectF candy, int resource) {
        Paint background = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
        canvas.drawBitmap(bitmap, null, candy, background);
    }

    //Metodo para renderizar los puntos
    private void renderPoints(Canvas canvas) {
        Paint points = new Paint();
        points.setTextSize(75);
        points.setTextAlign(Paint.Align.CENTER);
        points.setColor(Color.WHITE);
        canvas.drawText("Puntos: " + String.valueOf(score), 200, height - 20, points);
    }

    //Metodo para añadir puntos, podemos elegir cuantos queremos añadir
    private void addPoints(int[] fruit, int points) {
        score = score + points;
        Random random = new Random();
        fruit[0] = random.nextInt(width - FRUIT_SIZE) + FRUIT_SIZE;
        fruit[1] = random.nextInt(10000 - height) + height;
    }

    //Metodo para quitar puntos, podemos elegir cuantos queremos quitar
    private void removePoints(int[] candy, int points) {
        score--;
        Random random = new Random();
        candy[0] = random.nextInt(width - CANDY_SIZE) + CANDY_SIZE;
        candy[1] = random.nextInt(10000 - height) + height;
    }
}
