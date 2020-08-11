package com.juaracoding.iot;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashOpening extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_opening);

        Thread thread = new Thread() {
            public void run(){
                try{
                    sleep(4000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    startActivity(new Intent(SplashOpening.this, MainActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}
