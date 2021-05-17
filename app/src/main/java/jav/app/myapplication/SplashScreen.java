package jav.app.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    private int SLEEP_TIMER = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SplashScreen1 splashscreen = new SplashScreen1();
        splashscreen.start();
    }

    private class SplashScreen1 extends Thread{
        public void run(){
            try{
                sleep(1000 * SLEEP_TIMER);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            Intent intent = new Intent(jav.app.myapplication.SplashScreen.this, LoginActivity.class);
            startActivity(intent);
            jav.app.myapplication.SplashScreen.this.finish();
        }
    }
}