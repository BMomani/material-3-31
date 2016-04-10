package android.alcode.com.material.Uitility;

import android.alcode.com.material.R;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

/**
 * Created by MOMANI on 2016/04/09.
 */
public class PermissionUtility {
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;

    private Activity activity;
    private String permission;
    private int permissionRequestCode;
    private View mLayout;

    public PermissionUtility(Activity activity, String permission, int permissionRequestCode, View layout) {
        this.activity = activity;
        this.permission = permission;
        this.permissionRequestCode = permissionRequestCode;
        this.mLayout = layout;
    }


    public void requestPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isPermissionGranted())
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Log.i(activity.getClass().getSimpleName(),
                            "Displaying camera permission rationale to provide additional context.");
                    Snackbar.make(mLayout, "I cant run",
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(activity, new String[]{permission}, permissionRequestCode);
                                }
                            })
                            .show();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, permissionRequestCode);
                }
        }
    }

    public boolean isPermissionGranted() {
        return checkPermission() == PackageManager.PERMISSION_GRANTED;
    }

    private int checkPermission() {
        return ContextCompat.checkSelfPermission(activity, permission);
    }

}
