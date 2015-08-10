package com.example.gerardogtn.partyrock.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.ui.fragment.DetailFragment;
import com.example.gerardogtn.partyrock.ui.fragment.HomeListFragment;
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
    //listener for the RecyclerView in HomeListFragment
    private OnVenueClickListener mListener1;
    //listener for the ViewPager in DetailFragment
    private OnImageClickListener mListener2;


    //Listener interface for the RecyclerView in HomeListFragment
    public interface OnVenueClickListener {
        void onVenueClick();
    }
    //Listener interface for the ViewPager in DetailFragment
    public interface OnImageClickListener{
        void onImageClick(int position);
    }

    // Method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnVenueClickListener listener) {
        this.mListener1 = listener;
    }

    // Method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnImageClickListener listener) {
        this.mListener2 = listener;
    }

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
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.item_detail_image,container,false);

        //Method to identify the fragment where the Recyclerview is being executed.
        Fragment fragment = ((AppCompatActivity) mContext).getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof HomeListFragment){
        layout.setOnClickListener(new LinearLayout.OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener1.onVenueClick();
            }
        });} else if (fragment instanceof DetailFragment){
            layout.setOnClickListener(new LinearLayout.OnClickListener() {
                @Override
                public void onClick(View v) {
                  mListener2.onImageClick(position);
                }
            });
        }

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