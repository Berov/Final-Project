package com.artgallery;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artgallery.Util.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoughtItemsFragment extends Fragment {


    public BoughtItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        container.setPadding(Util.getScreenWidth()/10,Util.getScreenHeight()/20,Util.getScreenWidth()/10,Util.getScreenHeight()/20);
        return inflater.inflate(R.layout.fragment_bought_items, container, false);
    }

}
