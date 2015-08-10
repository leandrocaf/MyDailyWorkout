package leandrocaf.mydailyworkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                finish();

                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }
}
