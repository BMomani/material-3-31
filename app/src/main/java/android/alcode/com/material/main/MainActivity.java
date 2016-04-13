package android.alcode.com.material.main;

import android.alcode.com.material.R;
import android.alcode.com.material.detail.DetailActivity;
import android.alcode.com.material.models.Post;
import android.alcode.com.material.models.PostDetails;
import android.alcode.com.material.newpost.AddPostActivity;
import android.alcode.com.material.userprofile.UserProfileActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.firebase.client.Firebase;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PostAdapter.OnAdapterItemSelectedListener {

    public ArrayList<Post> list;
    protected DrawerLayout mDrawerLayout;
    private SearchBox search;
    private Toolbar toolbar;
    private Firebase mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        search = (SearchBox) findViewById(R.id.search_box);
        search.enableVoiceRecognition(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddPostActivity.class));
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupNavigationHeader(navigationView);
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new PostListFragment(), getString(R.string.explore));
        adapter.addFragment(new PostListFragment(), getString(R.string.highest_rated));
        adapter.addFragment(new PostListFragment(), getString(R.string.favourites));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    private void setupNavigationHeader(NavigationView navigationView) {
        //region find views
        final View header = navigationView.getHeaderView(0);
        ImageView userImageView = (ImageView) header.findViewById(R.id.imageView);
        TextView usernameTextView = (TextView) header.findViewById(R.id.username_textView);
        TextView emailTextView = (TextView) header.findViewById(R.id.email_textView);
        //endregion
        //region setBackgroundResource for header using glide
//        header.setBackgroundResource(R.drawable.side_nav_bar);
        Glide.with(this).load(R.drawable.side_nav_bar).asBitmap().into(new SimpleTarget<Bitmap>(304, (int) getResources().getDimension(R.dimen.nav_header_height)) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    header.setBackground(drawable);
                }
            }
        });
        //endregion
        //region add OnClickListener for header
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                UserProfileActivity.startUserProfileFromLocation(startingLocation, MainActivity.this);
                overridePendingTransition(0, 0);
            }
        });
        //endregion
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @SuppressWarnings("StatementWithEmptyBody")
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // Handle navigation view item clicks here.
                        int id = item.getItemId();

                        if (id == R.id.nav_camera) {
                            // Handle the camera action
                        } else if (id == R.id.nav_gallery) {

                        } else if (id == R.id.nav_slideshow) {

                        } else if (id == R.id.nav_manage) {

                        } else if (id == R.id.nav_share) {

                        } else if (id == R.id.nav_send) {

                        }

                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
            openSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void closeSearch() {
        search.hideCircularly(this);
        if (search.getSearchText().isEmpty()) toolbar.setTitle("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search.setSearchString(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void openSearch() {
        search.setLogoText("");
        search.revealFromMenuItem(R.id.action_search, this);

        search.setMenuListener(new SearchBox.MenuListener() {

            @Override
            public void onMenuClick() {
            }

        });
        search.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
                // Use this to tint the screen

            }

            @Override
            public void onSearchClosed() {
                // Use this to un-tint the screen
                closeSearch();
                toolbar.setTitle(getResources().getString(R.string.app_name));
            }

            @Override
            public void onSearchTermChanged(String s) {

            }


            @Override
            public void onSearch(String searchTerm) {
                Toast.makeText(MainActivity.this, searchTerm + " Searched",
                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResultClick(SearchResult searchResult) {

            }

            @Override
            public void onSearchCleared() {

            }

        });
    }

    @Override
    public void onItemSelected(View v, PostDetails id) {


        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        DetailActivity.startDetailActivityFromLocation(startingLocation, id, MainActivity.this);
        overridePendingTransition(0, 0);

//        Intent postDetailIntent = new Intent(MainActivity.this, DetailActivity.class);
        // postDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        postDetailIntent.putExtra("id", id);


        //postDetailIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //postDetailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(postDetailIntent);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }


    }
}
