package com.example.gerardogtn.partyrock.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.adapter.VenueRecyclerViewAdapter;
import com.example.gerardogtn.partyrock.data.LatLng;
import com.example.gerardogtn.partyrock.data.Venue;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VenueListFragment extends Fragment {
    public ArrayList<Venue> venues = new ArrayList<>();
    @Bind(R.id.recycler_view_venue)
    RecyclerView recyclerView;

    public static VenueListFragment newInstance() {
        VenueListFragment fragment = new VenueListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public VenueListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        ButterKnife.bind(this, view);
//An Api request to get the venues should be done here. Dummy values are implemented meanwhile.
        LinearLayoutManager llm=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        VenueRecyclerViewAdapter myAdapter =new VenueRecyclerViewAdapter(getActivity(), addVenues());
        recyclerView.setAdapter(myAdapter);
        

        return view;
    }
//Creation of dummy values to verify interaction
    private ArrayList<Venue> addVenues() {
        ArrayList<Venue> venueList = new ArrayList<>();
        ArrayList<String> gerryImgs = new ArrayList<>();
        gerryImgs.add("http://voidlive.com/wp-content/uploads/2012/12/adidas_house_party-610x406.jpg");
        gerryImgs.add("http://pad3.whstatic.com/images/thumb/2/29/Have-an-Awesome-Home-Party-Step-5.jpg/670px-Have-an-Awesome-Home-Party-Step-5.jpg");
        ArrayList<String> emilioImgs = new ArrayList<>();
        emilioImgs.add("http://cdn.crushable.com/files/2012/04/Screen-shot-2012-04-25-at-1.15.17-PM1.png");
        emilioImgs.add("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSN4qVjdtY6trL8jzTajCc_Rdp63dAvdJYcg8PbI8NcrRcIj-IEog");
        emilioImgs.add("http://voidlive.com/wp-content/uploads/2012/12/adidas_house_party-610x406.jpg");
        ArrayList<String> mariaImgs = new ArrayList<>();
        mariaImgs.add("http://voidlive.com/wp-content/uploads/2012/12/adidas_house_party-610x406.jpg");
        mariaImgs.add("http://cdn.crushable.com/files/2012/04/Screen-shot-2012-04-25-at-1.15.17-PM1.png");
        ArrayList<String> danImgs = new ArrayList<>();
        danImgs.add("http://voidlive.com/wp-content/uploads/2012/12/adidas_house_party-610x406.jpg");
        danImgs.add("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSN4qVjdtY6trL8jzTajCc_Rdp63dAvdJYcg8PbI8NcrRcIj-IEog");
        ArrayList<String> xaviImgs = new ArrayList<>();
        xaviImgs.add("http://voidlive.com/wp-content/uploads/2012/12/adidas_house_party-610x406.jpg");
        xaviImgs.add("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSN4qVjdtY6trL8jzTajCc_Rdp63dAvdJYcg8PbI8NcrRcIj-IEog");
        String[] venueName={"Gerry venue","Emilio venue","Maria venue","Dan venue","Xavi venue"};
        LatLng[] latLngDummyValues={new LatLng(5.2,3.4),new LatLng(5.2,3.4),new LatLng(5.2,3.4),new LatLng(5.2,3.4),new LatLng(5.2,3.4)};
        ArrayList<ArrayList<String>> venueImages= new ArrayList<>();
        Double[] sizeDummyValues={123.0,1342.2,42.0,12344.12,2.0};
        Double[] priceDummyValues={32.3,32.3,32.3,32.3,32.3};
        venueImages.add(gerryImgs);
        venueImages.add(emilioImgs);
        venueImages.add(mariaImgs);
        venueImages.add(danImgs);
        venueImages.add(xaviImgs);


        for (int i=0; i<8;i++){
            for (int j=0;j<venueName.length;j++){
                venueList.add(new Venue(venueName[j],latLngDummyValues[j],venueImages.get(j),sizeDummyValues[j],priceDummyValues[j]));
            }
        }
        return venueList;
    }

}
