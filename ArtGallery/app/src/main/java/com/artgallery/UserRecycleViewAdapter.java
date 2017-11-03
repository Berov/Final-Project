package com.artgallery;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artgallery.Model.Item;
import com.artgallery.Util.Util;

import java.util.List;

/**
 * Created by Berov on 1.11.2017 г..
 */

public class UserRecycleViewAdapter extends RecyclerView.Adapter<UserRecycleViewAdapter.VH> {

    private List<Item> items;
    private Context context;

    public UserRecycleViewAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(context);
        View row = li.inflate(R.layout.item__row, parent, false);
        VH vh = new VH(row);
        return vh;

    }

    @Override
    public void onBindViewHolder(VH holder, int position) {


        Item item = items.get(position);


        holder.itemImage.setImageBitmap(item.getImage());
        holder.title.setText("Title: " + item.getTitle());
        holder.price.setText("Price: " + String.valueOf(item.getPrice()));


        holder.topView.setBackgroundColor(Color.argb(255, 88, 24, 69));

        if (position % 2 == 0) {
            holder.topView.setBackgroundColor(Color.argb(255, 144, 12, 63));
        }
        if (position % 3 == 0) {
            holder.topView.setBackgroundColor(Color.argb(255, 199, 0, 57));
        }
        if (position % 4 == 0) {
            holder.topView.setBackgroundColor(Color.argb(255, 255, 87, 51));
        }
        if (position % 5 == 0) {
            holder.topView.setBackgroundColor(Color.argb(255, 255, 195, 0));
        }


        if (item.getDescription().length() < 40) {
            holder.description.setText("Description: " + item.getDescription());
        } else {
            holder.description.setText("Description: " + item.getDescription().substring(0, 40) + "...");
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    class VH extends RecyclerView.ViewHolder {
        View row;
        ImageView itemImage;
        TextView price;
        TextView title;
        TextView description;
        View topView;
        private int position;


        public VH(View row) {
            super(row);

            this.row = row;
            itemImage = (ImageView) row.findViewById(R.id.item_row_image);
            price = (TextView) row.findViewById(R.id.item_row_price);
            title = (TextView) row.findViewById(R.id.item_row_title);
            description = (TextView) row.findViewById(R.id.item_row_description);
            topView = row.findViewById(R.id.item_row_top_view);


            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Util.hideSoftKeyboard((Activity) context);
                    itemImage.requestFocus();
                    Toast.makeText(context, "dsadsadasdsa " + price.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
