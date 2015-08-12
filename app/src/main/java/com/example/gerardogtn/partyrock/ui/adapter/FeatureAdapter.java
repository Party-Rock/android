package com.example.gerardogtn.partyrock.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Feature;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emilio on 05/08/2015.
 */
public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder> {
    private Context mContext;
    private List<Feature> mFeatures = new ArrayList<>();
    private LayoutInflater mInflater;


    public FeatureAdapter(Context context, List<Feature> features) {
        this.mFeatures = features;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }


    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS: Returns a new inflated view for an item.
    @Override
    public FeatureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mInflater.inflate(R.layout.item_feature, parent, false);
        return new FeatureViewHolder(item);
    }


    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS:  Populates a view with the data of a Venue.
    @Override
    public void onBindViewHolder(FeatureViewHolder holder, int position) {
        holder.setData(mFeatures.get(position));
    }

    @Override
    public int getItemCount() {
        return mFeatures.size();
    }

    public class FeatureViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.feature_image)
        ImageView mIcon;

        @Bind(R.id.feature_text)
        TextView mText;

        public FeatureViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // REQUIRES: None.
        // MODIFIES: this.
        // EFFECTS:  Represents and visualizes venue data with views.
        public void setData(Feature feature) {
            mText.setText(feature.getDescription());
            mIcon.setImageResource(feature.getImageResource());
        }
    }
}
