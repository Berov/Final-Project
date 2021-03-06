package com.artgallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artgallery.Model.Item;

import java.util.ArrayList;

public class BoughtItemsFragment extends Fragment {

    public BoughtItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList<Item> items = ItemActivity.boughtItems;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = new RecyclerView(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ItemsActivityRecycleViewAdapter(getContext(), items, R.color.color5));

        return recyclerView;
    }
}
