package com.example.gerardogtn.partyrock.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.ui.activity.DetailActivity;
import com.example.gerardogtn.partyrock.ui.activity.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emilio on 02/08/2015.
 */
public class ImagePagerAdapterCenterImage extends PagerAdapter {

    @Bind(R.id.img_detail)
    ImageView mVenueImage;

    private Context mContext;
    private List<String> mImageUrls;
    private LayoutInflater mInflater;
    private OnVenueClickListener mVenueListener;
    private OnImageClickListener mImageListener;

    public ImagePagerAdapterCenterImage(Context context, List<String> imageUrls) {
        super();
        this.mContext = context;
        this.mImageUrls = imageUrls;
        mInflater = LayoutInflater.from(context);
    }

    // Method that allows the parent activity or fragment to define the listener
    public void setOnVenueListener(OnVenueClickListener listener) {
        this.mVenueListener = listener;
    }

    // Method that allows the parent activity or fragment to define the listener
    public void setOnImageListener(OnImageClickListener listener) {
        this.mImageListener = listener;
    }


    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.item_detail_image_center_image, container, false);
        setUpListener(layout, position);
        ButterKnife.bind(this, layout);
        Picasso.with(mContext)
                .load(mImageUrls.get(position))
                .error(R.mipmap.ic_launcher)
                .into(mVenueImage);
        container.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((View) object);
    }


    // REQUIRES: position is valid.
    // MODIFIES: this.
    // EFFECTS: If the context of this activity is a HomeActivity, sets the mVenueListener. Else if
    //          the context of this activity is a DetailActivity, sets the mImageListener.
    private void setUpListener(LinearLayout layout, final int position) {
        if (mContext.getClass() == HomeActivity.class) {
            layout.setOnClickListener(new LinearLayout.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mVenueListener.onVenueClick();
                }
            });

        } else if (mContext.getClass() == DetailActivity.class) {
            layout.setOnClickListener(new LinearLayout.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageListener.onImageClick(position);
                }
            });

        }
    }


    public interface OnVenueClickListener {
        void onVenueClick();
    }

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

}