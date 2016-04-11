package android.alcode.com.material.main;

import android.alcode.com.material.R;
import android.alcode.com.material.models.PostDetails;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.Random;


/**
 * Created by MOMANI on 2016/03/22.
 */
public class PostAdapter extends FirebaseRecyclerAdapter<PostDetails, RecyclerView.ViewHolder> {


    private static final int LAYOUT_LARGE = 1;
    private static final int LAYOUT_SMALL = 2;
    // private final List<Post> mPostList;
    private final Activity mAct;
    private final OnAdapterItemSelectedListener mAdapterCallback;
    private int mDefaultColor;
    private LayoutInflater mInflater;
    private int lastPosition = -1;
    private int gridColumns;

    public PostAdapter(Class<PostDetails> modelClass, int modelLayout, Class<RecyclerView.ViewHolder> viewHolderClass, Query ref, Activity activity) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mAct = activity;

        gridColumns = activity.getResources().getInteger(R.integer.grid_columns);
        mDefaultColor = ContextCompat.getColor(activity.getApplicationContext(), (R.color.colorPrimaryTransparent));
        mInflater = (LayoutInflater) this.mAct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAdapterCallback = (OnAdapterItemSelectedListener) mAct;

    }

    @Override
    protected void populateViewHolder(RecyclerView.ViewHolder holder, final PostDetails post, int position) {
        //  final Post post = mPostList.get(position);
        switch (getItemViewType(position)) {
            case LAYOUT_SMALL:
                ((ViewHolderSmall) holder).imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAdapterCallback != null) {
                            mAdapterCallback.onItemSelected(post);
                        }
                    }
                });

              /*  Picasso.with(((ViewHolderSmall) holder).imageView.getContext()).load(post.getImageUrl())
                        .into(((ViewHolderSmall) holder).imageView);*/

                ((ViewHolderSmall) holder).titleView.setText(post.getTitle());

                Glide.with(((ViewHolderSmall) holder).imageView.getContext())
                        .load(post.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(((ViewHolderSmall) holder).imageView);
                //setAnimation(holder.itemView, position);
                break;
            case LAYOUT_LARGE:
                /*Picasso.with(((ViewHolderLarge) holder).imageView.getContext()).load(post.getImageUrl())
                        .into(((ViewHolderLarge) holder).imageView);*/
                Glide.with(((ViewHolderLarge) holder).imageView.getContext())
                        .load(post.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(((ViewHolderLarge) holder).imageView);
                ((ViewHolderLarge) holder).titleView.setText(post.getTitle());
                ((ViewHolderLarge) holder).overviewView.setText(post.getLongDescription());


                //setAnimation(holder.itemView, position);

                ((ViewHolderLarge) holder).imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAdapterCallback != null) {
                            mAdapterCallback.onItemSelected(post);
                        }
                    }
                });

                ((ViewHolderLarge) holder).readMoreView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAdapterCallback != null) {
                            mAdapterCallback.onItemSelected(post);
                        }
                    }
                });
        }
        setAnimation(holder.itemView, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == LAYOUT_SMALL) {
            View v = mInflater.inflate(R.layout.layout_holder_movie_small, parent, false);
            vh = new ViewHolderSmall(v);
        } else {
            View v = mInflater.inflate(R.layout.layout_holder_movie_large, parent, false);
            vh = new ViewHolderLarge(v);
        }
        return vh;
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(1000);
            anim.setDuration(new Random().nextInt(501));
            viewToAnimate.startAnimation(anim);
            //Animation animation = AnimationUtils.loadAnimation(mAct, android.R.anim.fade_in);
            //viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return ((position + 1) % (gridColumns + 1) == 0 ? LAYOUT_LARGE : LAYOUT_SMALL);
    }


    public interface OnAdapterItemSelectedListener {
        void onItemSelected(PostDetails id);
    }


    public class ViewHolderSmall extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleView;

        public ViewHolderSmall(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.image);
            titleView = (TextView) v.findViewById(R.id.title);
            MaterialRippleLayout.on(imageView)
                    .rippleColor(Color.parseColor("#FF0000"))
                    .rippleAlpha(0.2f)
                    .rippleHover(true)
                    .create();
        }
    }


    public class ViewHolderLarge extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleView, overviewView, readMoreView;

        public ViewHolderLarge(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.image);
            titleView = (TextView) v.findViewById(R.id.title);
            overviewView = (TextView) v.findViewById(R.id.overview);
            readMoreView = (TextView) v.findViewById(R.id.read_more);
            MaterialRippleLayout.on(imageView)
                    .rippleColor(Color.parseColor("#FF0000"))
                    .rippleAlpha(0.2f)
                    .rippleHover(true)
                    .create();
            MaterialRippleLayout.on(readMoreView)
                    .rippleColor(Color.parseColor("#FF0000"))
                    .rippleAlpha(0.2f)
                    .rippleHover(true)
                    .create();
        }
    }


}