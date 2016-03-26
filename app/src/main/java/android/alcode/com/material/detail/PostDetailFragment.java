package android.alcode.com.material.detail;

import android.alcode.com.material.R;
import android.alcode.com.material.databases.Database;
import android.alcode.com.material.models.PostDetails;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by MOMANI on 2016/03/23.
 */
public class PostDetailFragment extends Fragment {

    private String id;
    private PostDetails mPostDetails;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private ImageView mBackdrop;

    private FloatingActionButton fab;


    public PostDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post_detail, container, false);

        //grab any data from other Activity
        id = getActivity().getIntent().getStringExtra("id");
        mPostDetails = Database.getInstance().getMovieDetailsFromID(id);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_movie_details);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);
        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        mBackdrop = (ImageView) v.findViewById(R.id.backdrop);


        fab = (FloatingActionButton) v.findViewById(R.id.fab);


        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getContext(), R.color.transparent));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getContext(), R.color.transparent));


        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_detail);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_share) {
                    String[] data = {PostDetailFragment.this.mPostDetails.getTitle()};
                    startActivity(Intent.createChooser(shareIntent(data[0]), "Share Via"));
                    return true;
                }
                return true;
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        if (null != mPostDetails)
            Picasso.with(getContext()).load(mPostDetails.getImageUrl())
                    .into(mBackdrop, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap posterBitmap = ((BitmapDrawable) mBackdrop.getDrawable()).getBitmap();
                            Palette.from(posterBitmap).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    //container.setBackgroundColor(ColorUtils.setAlphaComponent(palette.getMutedColor(mDefaultColor), 190)); //Opacity
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        int colorDark = ContextCompat.getColor(getContext(), (R.color.colorPrimaryDark));
                                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                        getActivity().getWindow().setStatusBarColor(colorDark);
                                        fab.setBackgroundTintList(ColorStateList.valueOf(colorDark));
                                    }
                                    mCollapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(getContext(), (R.color.colorPrimaryTransparent)));
                                }
                            });
                        }

                        @Override
                        public void onError() {

                        }
                    });

        mCollapsingToolbarLayout.setTitle(mPostDetails.getTitle());

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (null != mPostDetails) {
            mAdapter = new PostDetailsAdapter(mPostDetails, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }

        //register our review content listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites(v);
            }
        });

        super.onActivityCreated(savedInstanceState);
    }


    private void addToFavorites(View v) {

    }

    public Intent shareIntent(String data) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.post_extra_subject));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, data);
        return sharingIntent;
    }
}