package com.example.gerardogtn.partyrock.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gerardogtn.partyrock.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class ImageFragment extends Fragment {


    public static final String KEY_IMG_URL = "IMG_URL";

    @Bind(R.id.img_detail)
    ImageView mImage;

    private String url = "";

    public ImageFragment() {

    }

    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS:  Returns a new instance with an empty imageUrl.
    public static ImageFragment newInstance() {
        return newInstance("");
    }

    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS:  Return a new instance of ImageFragment with an associated imageUrl.
    public static ImageFragment newInstance(String imageUrl) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(KEY_IMG_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_detail_image, container, false);
        ButterKnife.bind(this, view);
        url = getArguments().getString(KEY_IMG_URL);
        setUpImage();
        return view;
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Use picasso to populate mImage with the URL in this.getArguments
    private void setUpImage() {
        //String imageUrl = getArguments().getString(KEY_IMG_URL);
        Picasso.with(getActivity())
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.house_for_show)
                .into(mImage);
    }


}
