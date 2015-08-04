package com.example.gerardogtn.partyrock.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gerardogtn.partyrock.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emilio on 02/08/2015.
 */
public class ImagePagerAdapter extends PagerAdapter {

    @Bind(R.id.img_detail)
    ImageView mVenueImage;

    private Context mContext;
    private List<String> mImageUrls;
    private LayoutInflater layoutInflater;

    public ImagePagerAdapter(Context context, List<String> imageUrls) {
        super();
        this.mContext = context;
        this.mImageUrls = imageUrls;
        layoutInflater= LayoutInflater.from(context);
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
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.item_detail_image,container,false);
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
        ((ViewPager) container).removeView((View) object);
    }
}