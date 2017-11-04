package com.artgallery;

import android.os.Bundle;
import android.view.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.view.LayoutInflater;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsForSameFragment extends Fragment {


    View root;

    public ItemsForSameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_items_for_salle, container, false);
        return root;
    }

}