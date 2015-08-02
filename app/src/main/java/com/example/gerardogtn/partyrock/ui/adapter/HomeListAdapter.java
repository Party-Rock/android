package com.example.gerardogtn.partyrock.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.local.VenueDummy;
import com.example.gerardogtn.partyrock.ui.activity.HomeActivity;
import com.example.gerardogtn.partyrock.ui.fragment.ImageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeListViewHolder>{

    private FragmentActivity mActivity;
    private List<VenueDummy> mVenues = new ArrayList<>();
    private LayoutInflater mInflater;


    public HomeListAdapter(Context context, List<VenueDummy> venues, AppCompatActivity activity) {
        this.mVenues = venues;
        mInflater = LayoutInflater.from(context);
        mActivity = activity;
    }

    @Override
    public HomeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mInflater.inflate(R.layout.item_home_list, parent, false);
        return new HomeListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(HomeListViewHolder holder, int position) {
        holder.setData(mVenues.get(position));
    }

    @Override
    public int getItemCount() {
        return mVenues.size();
    }

    public class HomeListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.venue_images)
        ViewPager mImages;

        @Bind(R.id.txt_capacity)
        TextView mCapacity;

        @Bind(R.id.txt_distance)
        TextView mDistance;

        @Bind(R.id.txt_price)
        TextView mPrice;

        private int mImageCount;

        public HomeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(VenueDummy venueDummy) {
            mCapacity.setText(Integer.toString(venueDummy.getmCapacity()));
            mDistance.setText(Double.toString(venueDummy.getmDistance()));
            mPrice.setText(Double.toString(venueDummy.getmPrice()));
            mImageCount = venueDummy.getmImageCount();
            setUpViewPager(venueDummy.getImageResource());
        }

        private void setUpViewPager(Integer imageResource) {
            ImagePagerAdapter adapter = new ImagePagerAdapter(mActivity.getSupportFragmentManager());

            if (imageResource == -1){
                for(int i = 0; i < mImageCount; i++){
                    adapter.addFragment(ImageFragment.newInstance());
                }
            } else {
                for(int i = 0; i < mImageCount; i++){
                    adapter.addFragment(ImageFragment.newInstance(imageResource));
                }
            }

            mImages.setAdapter(adapter);
        }
    }
}
