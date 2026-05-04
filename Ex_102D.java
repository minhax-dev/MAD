package com.example.simplegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {

    private Paint paint;
    private float cx, cy;
    private float radius = 80;
    private int score = 0;
    private Random random;

    public interface ScoreListener {
        void onScoreUpdate(int score);
    }

    private ScoreListener listener;

    public GameView(Context context, ScoreListener listener) {
        super(context);

        this.listener = listener;

        paint = new Paint();
        paint.setColor(Color.RED);

        random = new Random();

        moveCircle();
    }

    private void moveCircle() {
        cx = random.nextInt(800) + 100;
        cy = random.nextInt(1200) + 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            float x = event.getX();
            float y = event.getY();

            float distance = (float) Math.sqrt(
                    Math.pow(x - cx, 2) + Math.pow(y - cy, 2)
            );

            if (distance < radius) {
                score++;
                moveCircle();
                listener.onScoreUpdate(score);
                invalidate(); // redraw
            }
        }
        return true;
    }
}