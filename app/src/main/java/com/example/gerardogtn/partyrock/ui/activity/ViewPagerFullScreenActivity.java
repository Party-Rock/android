package com.example.gerardogtn.partyrock.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.ui.adapter.ImagePagerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class ViewPagerFullScreenActivity extends AppCompatActivity {

    @Bind(R.id.venue_images)
    ViewPager mImages;
    @Bind(R.id.text_for_numbers)
    TextView numberTxt;
    @Bind(R.id.button)
    ImageButton shareButton;

    private Venue mVenue;
    private Bitmap mBitmap;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_full_screen);
        ButterKnife.bind(this);
        mVenue = EventBus.getDefault().removeStickyEvent(Venue.class);
        try {
            mPosition = EventBus.getDefault().removeStickyEvent(Integer.class);
        } catch (Exception e){e.printStackTrace();}
        if (mVenue == null) {
            mVenue = (Venue) savedInstanceState.getSerializable("lastVenue");
        }
        setUpViewPager(mVenue.getImageUrls());
        setUpShareButton();
        setUpInitialText();

    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    private void setUpInitialText() {
        numberTxt.setText((mPosition) + "/" + mVenue.getImageUrls().size());
    }


    private void setUpShareButton() {
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBitmap = getBitmapFromURL(mVenue.getImageUrls().get(mPosition-1));
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, getString(R.string.txt_party_share) + "App", null);
                Uri uri = Uri.parse(path);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.txt_party_share));
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(shareIntent, "Share image using"));
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        EventBus.getDefault().postSticky(mVenue);
        outState.putSerializable("lastVenue", mVenue);
        super.onSaveInstanceState(outState);
    }

    private void setUpViewPager(final List<String> imageUrls) {
        //Add a new List for infinite scrolling. (This way, the imageUrls list is not modified)
        List<String> infiniteList = new ArrayList<>();
        //Infinite scrolling is created using dummy list elements at the first and last position.
        infiniteList.addAll(imageUrls);
        infiniteList.add(imageUrls.get(0));
        infiniteList.add(0, infiniteList.get(imageUrls.size() - 1));
        //Update the position of the selected image
        mPosition++;
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, infiniteList);
        mImages.setAdapter(adapter);
        mImages.setCurrentItem(mPosition);

        //Add the animation effect between scrolls.
        mImages.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.85f;
            private static final float MIN_ALPHA = 0.5f;

            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationX(horzMargin - vertMargin / 2);
                    } else {
                        view.setTranslationX(-horzMargin + vertMargin / 2);
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    view.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }


        });

        //A Page change listener is used to create Infinite scrolling and to update screen elements.
        mImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Elements on the screen are updated according to the current position.
                //('else' elements are used only for a smooth transition)
                if (position != 0 & position!=mVenue.getImageUrls().size()+1) {
                    numberTxt.setText((position) + "/" + mVenue.getImageUrls().size());
                } else if (position==0){
                    numberTxt.setText(mVenue.getImageUrls().size()+ "/" + mVenue.getImageUrls().size());
                } else {
                    numberTxt.setText(1+ "/" + mVenue.getImageUrls().size());
                }
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //If positioned in the dummyvalues, the viewpager is redirected to the original item.
                if (state == ViewPager.SCROLL_STATE_IDLE) { //this is triggered when the switch to a new page is complete
                    final int lastPosition = mImages.getAdapter().getCount() - 1;
                    if (mPosition == lastPosition) {
                        mImages.setCurrentItem(1, false); //false so we don't animate
                        mPosition = 1;
                    } else if (mPosition == 0) {
                        mImages.setCurrentItem(lastPosition - 1, false);
                        mPosition = lastPosition - 1;
                    }
                }
            }

        });
    }

}
