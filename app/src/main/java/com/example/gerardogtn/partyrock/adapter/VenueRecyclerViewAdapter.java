package com.example.gerardogtn.partyrock.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.Venue;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emilio on 02/08/2015.
 */
public class VenueRecyclerViewAdapter extends RecyclerView.Adapter<VenueRecyclerViewAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Venue> venues;
    private LayoutInflater inflater;

    public VenueRecyclerViewAdapter(Context mContext, ArrayList<Venue> venues) {
        this.venues = venues;
        inflater = LayoutInflater.from(mContext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.venue_images)
        ViewPager venueImagesViewPager;
        @Bind(R.id.txt_capacity)
        TextView textCapacity;
        @Bind(R.id.txt_price)
        TextView textPrice;
        @Bind(R.id.txt_distance)
        TextView textDistance;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setVenue(Venue venue, Context context) {
            //Values are obtained from a venue object
            textCapacity.setText("Para "+ venue.getSize()+" amigos");
            textPrice.setText("$"+venue.getPrice());
            //A calculation to get the distance according to the current position must be
            // implemented instead of current toString method.
            textDistance.setText(venue.getLatLng().getLat().toString()+"km.");


            //Setting up the PagerAdapter for the Images of the Venue
            VenueImageListAdapter venueImageListAdapter = new VenueImageListAdapter(context, venue.getImageURL());
            venueImagesViewPager.setAdapter(venueImageListAdapter);

        }
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int)}. Since it will be re-used to display different
     * items in the data set, it is a good idea to cache references to sub views of the View to
     * avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_venue_list, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link ViewHolder#itemView} to reflect the item at
     * the given position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this
     * method again if the position of the item changes in the data set unless the item itself
     * is invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside this
     * method and should not keep a copy of it. If you need the position of an item later on
     * (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will have
     * the updated adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(VenueRecyclerViewAdapter.ViewHolder holder, int position) {
        mContext= holder.venueImagesViewPager.getContext();
        holder.setVenue(venues.get(position),mContext);
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return venues.size();
    }


}
