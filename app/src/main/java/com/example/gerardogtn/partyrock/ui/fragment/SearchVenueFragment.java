package com.example.gerardogtn.partyrock.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.service.SearchVenueEvent;
import com.example.gerardogtn.partyrock.ui.activity.SearchResultsActivity;
import com.example.gerardogtn.partyrock.ui.adapter.ClickRepeatListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchVenueFragment extends DialogFragment {
    @Bind(R.id.btn_search)
    Button mSearchButton;
    @Bind(R.id.btn_close)
    Button mCloseButton;
    @Bind(R.id.people_slider)
    SeekBar mSeekBarPeople;
    @Bind(R.id.price_slider)
    SeekBar mSeekBarPrice;
    @Bind(R.id.search_bar)
    EditText mEditTextSearch;

    @Bind(R.id.text_price)
    TextView mTxtPrice;
    @Bind(R.id.text_view_price)
    TextView mTxtViewPrice;
    @Bind(R.id.text_room)
    TextView mTxtPeople;


    @Bind(R.id.people_less)
    ImageView mLessPeopleImg;
    @Bind(R.id.people_plus)
    ImageView mMorePeopleImg;
    @Bind(R.id.money_less)
    ImageView mLessMoneyImg;
    @Bind(R.id.money_plus)
    ImageView mMoreMoneyImg;

    private String mLocation;

    //Arbitrary values for initial progress and max values on Seekbars.
    private int mPriceProgress = 2000;
    private final int mMaxPrice = 20000;
    private int mPeopleProgress = 50;
    private final int mMaxPeople = 400;


    public SearchVenueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_search_venues, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().registerSticky(this);
        setUpPriceSlider();
        setUpPeopleSlider();


        return view;
    }

    @OnClick(R.id.btn_close)
    public void onClickClsoeButton() {
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_search)
    public void searchOnClickEvent() {
        mLocation = mEditTextSearch.getText().toString();
        SearchVenueEvent searchVenueEvent = new SearchVenueEvent(mLocation, mPriceProgress, mPeopleProgress);
        EventBus.getDefault().post(searchVenueEvent);
        EventBus.getDefault().postSticky(searchVenueEvent);
        startActivity(new Intent(getActivity(), SearchResultsActivity.class));

        getDialog().dismiss();
    }

    //EventBus method to save Search Parameters
    public void onEvent(SearchVenueEvent searchVenueEvent) {
        String location = searchVenueEvent.getLocation();
        int price = searchVenueEvent.getPrice();
        int capacity = searchVenueEvent.getCapacity();
        mLocation = location;
        mPriceProgress = price;
        mPeopleProgress = capacity;

        mEditTextSearch.setText(location);
    }

    private void setUpPriceSlider() {
        mSeekBarPrice.setMax(mMaxPrice);
        mSeekBarPrice.setProgress(mPriceProgress);
        mTxtPrice.setText("$" + mPriceProgress);

        //Syncing text and slider
        mSeekBarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTxtPrice.setText("$" + progress);
                mPriceProgress = progress;
                //Change text value if price is maxed
                if (progress == mMaxPrice) {
                    mTxtViewPrice.setText(R.string.alt_price_text);
                } else {
                    mTxtViewPrice.setText(R.string.price_text);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Images OnClick listeners
        //Money Increase
        mMoreMoneyImg.setOnTouchListener(new ClickRepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Max Price validation
                if (mPriceProgress < mMaxPrice - 500) {
                    mSeekBarPrice.setProgress(mPriceProgress += 500);
                } else {
                    mSeekBarPrice.setProgress(mMaxPrice);
                }
            }
        }));
        //Money Decrease
        mLessMoneyImg.setOnTouchListener(new ClickRepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPriceProgress > 500) {
                    mSeekBarPrice.setProgress(mPriceProgress -= 500);
                } else {
                    mSeekBarPrice.setProgress(200);
                }
            }
        }));
    }

    private void setUpPeopleSlider() {
        mSeekBarPeople.setMax(mMaxPeople);
        mSeekBarPeople.setProgress(mPeopleProgress);
        mTxtPeople.setText(mPeopleProgress + "+ People");

        //Syncing text and slider
        mSeekBarPeople.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTxtPeople.setText(progress + "+ People");
                mPeopleProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Images OnClick listeners
        //People Increase
        mMorePeopleImg.setOnTouchListener(new ClickRepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPeopleProgress < mMaxPeople - 10) {
                    mSeekBarPeople.setProgress(mPeopleProgress += 10);
                } else {
                    mSeekBarPeople.setProgress(mMaxPeople);
                }
            }
        }));
        //People Decrease
        mLessPeopleImg.setOnTouchListener(new ClickRepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPeopleProgress > 15) {
                    mSeekBarPeople.setProgress(mPeopleProgress -= 5);
                } else {
                    mSeekBarPeople.setProgress(10);
                }
            }
        }));
    }


}
