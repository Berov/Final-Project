package com.artgallery;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.artgallery.Model.DBManager;
import com.artgallery.Model.Item;
import com.artgallery.Model.User;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    private FragmentManager fm = getSupportFragmentManager();
    private Fragment fragmentForSale = new ItemsForSameFragment();
    private Fragment fragmentSold = new SoldItemsFragment();
    private Fragment fragmentBought = new BoughtItemsFragment();
    public static final int GALLERY_REQUEST = 30;
    public static User user;
    public static ArrayList<Item> itemsForSale = new ArrayList<>();
    public static ArrayList<Item> soldItems = new ArrayList<>();
    public static ArrayList<Item> boughtItems = new ArrayList<>();
    private ArrayAdapter<CharSequence> arrTypesAdapter;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), 3);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        user = (User) bundle.getSerializable("USER");

        Toast.makeText(this, "User - " + user.getName(), Toast.LENGTH_SHORT).show();

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_photo_black_24dp);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white));//TODO bind it with the Theme
        user.setSaleFlag(false);
        DBManager.getInstance(this).updateUser(user);

        itemsForSale = user.getItemsForSale();
        soldItems = user.getSoldItems();
        boughtItems = user.getBoughtItems();

        arrTypesAdapter = ArrayAdapter.createFromResource(ItemActivity.this, R.array.itemTypes, android.R.layout.simple_spinner_item);
        arrTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_new_item) {

            Intent intent = new Intent(ItemActivity.this, UploadItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("USER", user);
            intent.putExtras(bundle);
            startActivityForResult(intent, GALLERY_REQUEST);
            return true;

        } else if (id == android.R.id.home) {

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            Bundle bundle = new Bundle();
            bundle.putSerializable("USER", user);
            intent.putExtras(bundle);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            user = (User) data.getSerializableExtra("USER");
            itemsForSale = user.getItemsForSale();

            fm.beginTransaction().detach(fragmentForSale).attach(fragmentForSale).commit();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        Bundle bundle = new Bundle();
        bundle.putSerializable("USER", user);
        intent.putExtras(bundle);
        finish();
    }


//    public static class PlaceholderFragment extends Fragment {  //????????????????????????????????????????????????//TODO remove it
//
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//
//            return fragment;
//        }
//
//    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int number;


        public SectionsPagerAdapter(FragmentManager fm, int number) {
            super(fm);

            this.number = number;
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return fragmentSold;
                case 1:
                    return fragmentBought;
                case 2:
                    return fragmentForSale;
                default:
                    return null;
            }
        }


        @Override
        public int getCount() {

            return number;
        }


        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Sold items";
                case 1:
                    return "Bought items";
                case 2:
                    return "Items for sale";
            }

            return null;
        }
    }
}
