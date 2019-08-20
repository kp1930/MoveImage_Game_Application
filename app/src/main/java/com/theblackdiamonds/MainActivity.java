package com.theblackdiamonds;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int screenWidth, screenHeight;
    private ImageView arrowUp, arrowDown, arrowRight, arrowLeft;
    private Button pauseBtn;
    private float arrowUpX, arrowUpY, arrowDownX, arrowDownY, arrowRightX, arrowRightY, arrowLeftX, arrowLeftY;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private boolean pause_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrowUp = findViewById(R.id.arrowUp);
        arrowDown = findViewById(R.id.arrowDown);
        arrowRight = findViewById(R.id.arrowRight);
        arrowLeft = findViewById(R.id.arrowLeft);
        pauseBtn = findViewById(R.id.pauseBtn);

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        arrowUp.setX(-80.0f);
        arrowUp.setY(-80.0f);
        arrowDown.setX(-80.0f);
        arrowDown.setY(screenHeight + 80.0f);
        arrowRight.setX(screenWidth + 80.0f);
        arrowRight.setY(-80.0f);
        arrowLeft.setX(-80.0f);
        arrowLeft.setY(-80.0f);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        }, 0, 20);
    }

    public void changePos() {
        arrowUpY -= 10;
        if (arrowUp.getY() + arrowUp.getHeight() < 0) {
            arrowUpX = (float) Math.floor(Math.random() * (screenWidth - arrowUp.getWidth()));
            arrowUpY = screenHeight + 100.0f;
        }
        arrowUp.setX(arrowUpX);
        arrowUp.setY(arrowUpY);

        arrowDownY += 10;
        if (arrowDown.getY() > screenHeight) {
            arrowDownX = (float) Math.floor(Math.random() * (screenWidth - arrowDown.getWidth()));
            arrowDownY = -100.0f;
        }
        arrowDown.setX(arrowDownX);
        arrowDown.setY(arrowDownY);

        arrowRightX += 10;
        if (arrowRight.getX() > screenWidth) {
            arrowRightX = -100.0f;
            arrowRightY = (float) Math.floor(Math.random() * (screenHeight - arrowRight.getHeight()));
        }
        arrowRight.setX(arrowRightX);
        arrowRight.setY(arrowRightY);

        arrowLeftX -= 10;
        if (arrowLeft.getX() + arrowLeft.getWidth() < 0) {
            arrowLeftX = screenWidth + 100.0f;
            arrowLeftY = (float) Math.floor(Math.random() * (screenHeight - arrowLeft.getHeight()));
        }
        arrowLeft.setX(arrowLeftX);
        arrowLeft.setY(arrowLeftY);

    }

    @SuppressLint("SetTextI18n")
    public void pausePushed(View view) {

        if (!pause_flg) {
            pause_flg = true;
            timer.cancel();
            timer = null;
            pauseBtn.setText("START");
        } else {
            pause_flg = false;
            pauseBtn.setText("PAUSE");

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);
        }
    }
}