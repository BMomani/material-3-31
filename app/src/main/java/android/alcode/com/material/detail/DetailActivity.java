package android.alcode.com.material.detail;

import android.alcode.com.material.R;
import android.alcode.com.material.models.PostDetails;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    public static void startDetailActivityFromLocation(int[] startingLocation, PostDetails id, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, DetailActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        intent.putExtra("id", id);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
    }
}
