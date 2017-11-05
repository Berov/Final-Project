package com.artgallery;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artgallery.Model.DBManager;
import com.artgallery.Model.Item;
import com.artgallery.Model.User;
import com.artgallery.Util.Util;

import java.util.List;

/**
 * Created by Berov on 5.11.2017 г..
 */


public class ItemsActivityRecycleViewAdapter extends RecyclerView.Adapter<ItemsActivityRecycleViewAdapter.VH> {

    private List<Item> items;
    private Context context;
    private int color;


    ItemsActivityRecycleViewAdapter(Context context, List<Item> items, int color) {

        this.context = context;
        this.items = items;
        this.color = color;
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

        final Item item = items.get(position);

        holder.itemImage.setImageBitmap(item.getImage());
        holder.title.setText("Title: " + item.getTitle());
        holder.price.setText("Price: " + String.valueOf(item.getPrice()));

//        holder.topView.setBackgroundColor(color);

        if (item.getDescription().length() < 40) {
            holder.description.setText("Description: " + item.getDescription());
        } else {
            holder.description.setText("Description: " + item.getDescription().substring(0, 40) + "...");
        }

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Util.hideSoftKeyboard((Activity) context);
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LinearLayout showFullItem = new LinearLayout(context);

                TextView title = new TextView(context);
                TextView price = new TextView(context);
                TextView author = new TextView(context);
                TextView description = new TextView(context);
                ImageView image = new ImageView(context);

                title.setText("Title:  " + item.getTitle());
                price.setText("Price:  " + String.valueOf(item.getPrice()) + "€");

                if (!item.getAuthor().isEmpty() && item.getAuthor() != null) {
                    author.setText("Author: " + item.getAuthor());
                }

                image.setImageBitmap(item.getImage());
                image.setMinimumHeight(Util.getScreenWidth() / 10 * 7);
                image.setMaxHeight(Util.getScreenWidth() / 10 * 7);
                image.setPadding(8, 8, 8, 8);
                description.setText("Description: \n" + item.getDescription());


                showFullItem.setOrientation(LinearLayout.VERTICAL);
                showFullItem.setGravity(LinearLayout.TEXT_ALIGNMENT_CENTER);
                showFullItem.setPadding(16, 8, 16, 8);
                showFullItem.addView(title);
                showFullItem.addView(price);
                showFullItem.addView(author);
                showFullItem.addView(image);
                showFullItem.addView(description);

                alert
                        .setView(showFullItem)
//                        .setMessage("Do you want to buy this item?")
                        .setCancelable(true)

                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });
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

        private VH(View row) {
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
//                    Util.hideSoftKeyboard((Activity) context);
                    itemImage.requestFocus();
                }
            });
        }
    }
}
