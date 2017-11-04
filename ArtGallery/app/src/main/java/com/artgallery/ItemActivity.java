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

import com.artgallery.Model.User;

public class ItemActivity extends AppCompatActivity {
    FragmentManager fm = getSupportFragmentManager();
//    Fragment a = fm.findFragmentById(R.id.add_item_scroll_view);

    public static final int GALLERY_REQUEST = 30;

    //    private Button btnCancel, btnUpload;
//
    private EditText txtTitle, txtPrice, txtDesc, txtAuthor;
    //
    private Spinner spKind, spType;
    private String type, subtype;
    private ImageView imgPreview;
    private int userID;
    //    private int ownerID;
    private double price;

    private boolean isTitleTrue, isDescTrue, isAuthorTrue, isPriceTrue = false;
    private boolean imageChanged = false;
    private byte[] bytes;
    private User user;


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

        user = (User) getIntent().getSerializableExtra("USER");


        ArrayAdapter<CharSequence> arrTypesAdapter = ArrayAdapter.createFromResource(ItemActivity.this, R.array.itemTypes, android.R.layout.simple_spinner_item);
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

        if (id == R.id.action_settings) {
            Intent intent = new Intent(ItemActivity.this, UploadItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("USER", user);
            intent.putExtras(bundle);
            startActivityForResult(intent, GALLERY_REQUEST);
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
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
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_item, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
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
                    return new SoldItemsFragment();
                case 1:
                    return new BoughtItemsFragment();
                case 2:
                    return new ItemsForSameFragment();
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
