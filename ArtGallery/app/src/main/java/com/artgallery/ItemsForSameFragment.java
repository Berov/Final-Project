package com.artgallery;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artgallery.Model.Item;

import java.util.ArrayList;


public class ItemsForSameFragment extends Fragment {

    private ArrayList<Item> items ;
    private LinearLayoutManager lm ;
    private RecyclerView rv ;


//    private ArrayList<Item> items = ItemActivity.itemsForSale;
//    private LinearLayoutManager lm = new LinearLayoutManager(getContext());
//    private RecyclerView rv = new RecyclerView(getContext());

//    String[] strings = {"1", "2", "3", "4", "5", "6", "7"};
//    View rootView;
//    RecyclerView recyclerView;

    public ItemsForSameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        items = ItemActivity.itemsForSale;
        lm = new LinearLayoutManager(getContext());
        rv = new RecyclerView(getContext());


//        RecyclerView rv = new RecyclerView(getContext());
//        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setLayoutManager(lm);
        rv.setAdapter(new UserRecycleViewAdapter(getContext(), items, ItemActivity.user));
        return rv;
    }


    @Override
    public void onResume() {
        super.onResume();

//        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }

    public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
        private String[] dataSource;

        public SimpleRVAdapter(String[] dataArgs) {
            dataSource = dataArgs;
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = new TextView(parent.getContext());
            SimpleViewHolder viewHolder = new SimpleViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource[position]);
        }

        @Override
        public int getItemCount() {
            return dataSource.length;
        }
    }

    /**
     * A Simple ViewHolder for the RecyclerView
     */
    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

}