package com.artgallery;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
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



//        Post current = Post.get(position);
//
//        holder.txtTitle.setText(current.getTitle());
//
//        // add this line
//        holder.id = Post.get(postion).getId();





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
        private int position;


        public VH(View row) {
            super(row);

            this.row = row;
            itemImage = (ImageView) row.findViewById(R.id.item_row_image);
            price = (TextView) row.findViewById(R.id.item_row_price);
            title = (TextView) row.findViewById(R.id.item_row_title);
            description = (TextView) row.findViewById(R.id.item_row_description);



            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "dsadsadasdsa " + price.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
