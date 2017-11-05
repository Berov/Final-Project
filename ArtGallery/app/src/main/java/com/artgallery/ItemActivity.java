package com.artgallery;

import android.content.Intent;
import android.support.design.widget.TabLayout;
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

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.artgallery.Model.Item;
import com.artgallery.Model.User;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {
    private FragmentManager fm = getSupportFragmentManager();

    private Fragment fragmentForSale = new ItemsForSameFragment();
    private Fragment fragmentSold = new SoldItemsFragment();
    private Fragment fragmentBought = new BoughtItemsFragment();
    public static final int GALLERY_REQUEST = 30;


    //    private EditText txtTitle, txtPrice, txtDesc, txtAuthor;
//    //
//    private Spinner spKind, spType;
//    private String type, subtype;
//    private ImageView imgPreview;
//    private int userID;
//    private double price;
//
//    private boolean isTitleTrue, isDescTrue, isAuthorTrue, isPriceTrue = false;
//    private boolean imageChanged = false;
//    private byte[] bytes;
    public static User user;
    public static ArrayList<Item> itemsForSale;
    ArrayAdapter<CharSequence> arrTypesAdapter;


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


        //My tries -----------------------------------------------------------------------------------------------------


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        user = (User) bundle.getSerializable("USER");

        Toast.makeText(this, "User - " + user.getName(), Toast.LENGTH_SHORT).show();


//        user = (User) getIntent().getSerializableExtra("USER");
        itemsForSale = user.getItemsForSale();


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

//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
////        Toast.makeText(this, "onResume User Items - " + itemsForSale.size(), Toast.LENGTH_SHORT).show();
////        fm.beginTransaction().detach(fragmentForSale).attach(fragmentForSale).commit();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            user = (User) data.getSerializableExtra("USER");
            itemsForSale = user.getItemsForSale();

            Toast.makeText(this, "onResult User Items - " + itemsForSale.size(), Toast.LENGTH_LONG).show();


//            arrTypesAdapter = ArrayAdapter.createFromResource(ItemActivity.this, R.array.itemTypes, android.R.layout.simple_spinner_item);
//            arrTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
            fm.beginTransaction().detach(fragmentForSale).attach(fragmentForSale).commit();


//
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

    /**
     * A placeholder fragment containing fragmentForSale simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns fragmentForSale new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

    }


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
//                    return new SoldItemsFragment();
                    return fragmentSold;
                case 1:
//                    return new BoughtItemsFragment();
                    return fragmentBought;
                case 2:
//                    return new ItemsForSameFragment();
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
