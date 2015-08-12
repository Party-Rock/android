package com.example.gerardogtn.partyrock.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.service.VenueEvent;
import com.example.gerardogtn.partyrock.ui.activity.DetailActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeListViewHolder>{

    private Context mContext;
    private List<Venue> mVenues = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnVenueClickListener listener;

    //Listener interface
    public interface OnVenueClickListener {
        void onVenueClick(int position);
    }

    // Method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnVenueClickListener listener) {
        this.listener = listener;
    }


    public HomeListAdapter(Context context, List<Venue> venues) {
        this.mVenues = venues;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }


    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS: Returns a new inflated view for an item.
    @Override
    public HomeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mInflater.inflate(R.layout.item_venue_list, parent, false);
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

    public class HomeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.venue_images)
        ViewPager mImages;

        @Bind(R.id.txt_capacity)
        TextView mCapacity;

        @Bind(R.id.txt_price)
        TextView mPrice;

        private List<String> mImageUrls;

        public HomeListViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onVenueClick(getLayoutPosition());
            }
        }

        // REQUIRES: None.
        // MODIFIES: None.
        // EFFECTS: If places is less than zero, return a new IllegalArgumentException. Else,
        // create a new BigDecimal with value, rounds up to places and returns the double value.
        public double round(double value, int places) {
            if (places < 0) {
                throw new IllegalArgumentException();
            }

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

        // REQUIRES: None.
        // MODIFIES: this.
        // EFFECTS:  Represents and visualizes venue data with views.
        public void setData(Venue venue) {
            mCapacity.setText(""+ Integer.toString(venue.getCapacity()));
            mPrice.setText(venue.getFormattedPrice());
            mImageUrls = venue.getImageUrls();
            setUpViewPager(venue.getImageUrls());
        }


        // REQUIRES: None.
        // MODIFIES: this.
        // EFFECTS:  If the imageResource is invalid(-1) add a new ImageFragment with default Image.
        //           Otherwise, add a new ImageFragment with the specified image resource.
        private void setUpViewPager(List<String> imageUrls) {
            ImagePagerAdapter adapter = new ImagePagerAdapter(mContext, imageUrls);
            mImages.setAdapter(adapter);
            adapter.setOnVenueListener(new ImagePagerAdapter.OnVenueClickListener() {
                @Override
                public void onVenueClick() {
                    VenueEvent clickedVenue = new VenueEvent(mVenues.get(getLayoutPosition()));
                    EventBus.getDefault().postSticky(clickedVenue);
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}