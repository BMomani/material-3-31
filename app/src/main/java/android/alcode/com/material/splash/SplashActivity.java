package android.alcode.com.material.splash;

import android.alcode.com.material.Constants;
import android.alcode.com.material.R;
import android.alcode.com.material.main.MainActivity;
import android.alcode.com.material.registration.RegistrationActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.client.Firebase;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private CoordinatorLayout container;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.i("where", SplashActivity.class.getSimpleName());

        container = (CoordinatorLayout) findViewById(R.id.activity_splash_container);
        progressBar = (ProgressBar) findViewById(R.id.activity_splash_pb);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread timer = new Thread() {
            public void run() {
                loadAndParseConfig();
            }
        };
        timer.start();
    }

    private void loadAndParseConfig() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        mRef = new Firebase(Constants.mRef);
        if (mRef.getAuth() == null) {
            goTo(RegistrationActivity.class);
        } else {
            mRef.getAuth();
            goTo(MainActivity.class);
        }

    }

    private void goTo(Class<?> ActivityClass) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
        Intent openHome = new Intent(SplashActivity.this, ActivityClass);

        startActivity(openHome);
        finish();
    }

}
