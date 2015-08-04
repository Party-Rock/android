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

    private Context mContext;
    private List<VenueDummy> mVenues = new ArrayList<>();
    private LayoutInflater mInflater;


    public HomeListAdapter(Context context, List<VenueDummy> venues) {
        this.mVenues = venues;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }


    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS: Returns a new inflated view for an item.
    @Override
    public HomeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mInflater.inflate(R.layout.item_home_list, parent, false);
        return new HomeListViewHolder(item);
    }


    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS:  Populates a view with the data of a Venue.
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

        private List<String> mImageUrls;

        public HomeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // REQUIRES: None.
        // MODIFIES: this.
        // EFFECTS:  Represents and visualizes venue data with views.
        public void setData(VenueDummy venueDummy) {
            mCapacity.setText(Integer.toString(venueDummy.getmCapacity()));
            mDistance.setText(Double.toString(venueDummy.getmDistance()));
            mPrice.setText(Double.toString(venueDummy.getmPrice()));
            mImageUrls = venueDummy.getImageUrls();
            setUpViewPager(venueDummy.getImageUrls());
        }


        // REQUIRES: None.
        // MODIFIES: this.
        // EFFECTS:  If the imageResource is invalid(-1) add a new ImageFragment with default Image.
        //           Otherwise, add a new ImageFragment with the specified image resource.
        private void setUpViewPager(List<String> imageUrls) {
            ImagePagerAdapter adapter = new ImagePagerAdapter(mContext, imageUrls);
            mImages.setAdapter(adapter);
        }
    }
}
