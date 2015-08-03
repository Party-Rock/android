package com.example.gerardogtn.partyrock.adapter;

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
public class VenueImageListAdapter extends PagerAdapter {
    @Bind(R.id.img_detail)
    ImageView venueImage;
    Context context;
    private List<String> imagesURI;
    LayoutInflater layoutInflater;

    public VenueImageListAdapter(Context context, List<String> imagesURI) {
        super();
        this.context = context;
        this.imagesURI = imagesURI;
        layoutInflater= LayoutInflater.from(context);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return imagesURI.size();
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View in which the page will be shown.
     * @param position  The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.item_detail_image,container,false);
        ButterKnife.bind(this,layout);
        //An exception must be thrown if there are no images to load.
        Picasso.with(context).load(imagesURI.get(position)).into(venueImage);
//Home
        container.addView(layout);
        return layout;
    }

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View from which the page will be removed.
     * @param position  The page position to be removed.
     * @param object    The same object that was returned by
     *                  {@link #instantiateItem(View, int)}.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
