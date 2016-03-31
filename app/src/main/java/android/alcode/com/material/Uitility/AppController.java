package android.alcode.com.material.Uitility;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by MOMANI on 2016/03/30.
 */
public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        // other setup code
    }

}
