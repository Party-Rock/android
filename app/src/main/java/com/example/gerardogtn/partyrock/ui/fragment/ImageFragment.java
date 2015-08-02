package com.example.gerardogtn.partyrock.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gerardogtn.partyrock.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class ImageFragment extends Fragment {


    public static final String KEY_IMG_RESOURCE = "IMG_ID";

    @Bind(R.id.img_detail)
    ImageView image;

    public ImageFragment() {

    }

    public static ImageFragment newInstance() {
        return newInstance(-1);

    }

    public static ImageFragment newInstance(Integer imageResource) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_IMG_RESOURCE, imageResource);
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
        setUpImage();
        return view;
    }

    private void setUpImage() {
        int imgResource = getArguments().getInt(KEY_IMG_RESOURCE);
        if (imgResource != -1){
            image.setImageResource(imgResource);
        }
    }


}
