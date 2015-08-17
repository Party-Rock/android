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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_full_screen);
        ButterKnife.bind(this);
        mVenue = EventBus.getDefault().removeStickyEvent(Venue.class);
        if (mVenue == null) {
            mVenue = (Venue) savedInstanceState.getSerializable("lastVenue");
        }
        setUpViewPager(mVenue.getImageUrls());
        setUpShareButton();
        setUpInitialText();
        setUpInitialShare();

    }



    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }



    private void setUpInitialShare() {
        mBitmap = getBitmapFromURL(mVenue.getImageUrls().get(0));
    }

    private void setUpInitialText() {
        numberTxt.setText("1/"+mVenue.getImageUrls().size());
    }


    private void setUpShareButton() {
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, getString(R.string.txt_party_share)+ "App", null);
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
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageUrls);
        mImages.setAdapter(adapter);
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

        mImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                numberTxt.setText((position + 1) + "/" + mVenue.getImageUrls().size());
                mBitmap = getBitmapFromURL(mVenue.getImageUrls().get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
    }

}
