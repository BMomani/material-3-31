package android.alcode.com.material;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by MOMANI on 2016/03/30.
 */
public class BeActiveApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        // other setup code
    }

}
