package android.alcode.com.material.newpost;

import android.Manifest;
import android.alcode.com.material.R;
import android.alcode.com.material.utils.ImagePicker;
import android.alcode.com.material.utils.PermissionUtility;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter

    private EditText editTextTitle, editTextSubtitle, editTextDescription;
    private ImageView imageViewBack, imageViewPoster;
    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        mLayout = findViewById(R.id.root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editTextTitle = (EditText) findViewById(R.id.edit_text_title);
        editTextSubtitle = (EditText) findViewById(R.id.edit_text_subtitle);
        editTextDescription = (EditText) findViewById(R.id.edit_text_description);
        imageViewBack = (ImageView) findViewById(R.id.image_view_back);
        imageViewPoster = (ImageView) findViewById(R.id.image_view_poster);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "added to firebase", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imageViewBack.setOnClickListener(this);
        imageViewPoster.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_post_avtivity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_send:
                //FabClickAddOrEditAlarm(null,null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);

        switch (requestCode) {
            case PICK_IMAGE_ID + 1:
                imageViewBack.setImageBitmap(bitmap);
                //Glide.with(this).load(bitmap).into(imageViewBack);
                break;
            case PICK_IMAGE_ID + 2:
                imageViewPoster.setImageBitmap(bitmap);
                //Glide.with(this).load(bitmap).into(imageViewPoster);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        PermissionUtility permissionUtility = new PermissionUtility(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, PermissionUtility.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE, mLayout);
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        permissionUtility.requestPermissions();
        if (permissionUtility.isPermissionGranted())
            switch (v.getId()) {
                case R.id.image_view_back:
                    startActivityForResult(chooseImageIntent, PICK_IMAGE_ID + 1);
                case R.id.image_view_poster:
                    startActivityForResult(chooseImageIntent, PICK_IMAGE_ID + 2);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermissionUtility.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
