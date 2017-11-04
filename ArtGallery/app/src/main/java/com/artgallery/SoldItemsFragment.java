package com.artgallery;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artgallery.Util.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class SoldItemsFragment extends Fragment {


    public SoldItemsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        container.setPadding(Util.getScreenWidth()/10,Util.getScreenHeight()/40,Util.getScreenWidth()/10,Util.getScreenHeight()/10);
        return inflater.inflate(R.layout.fragment_sold_items, container, false);
    }

}
