package com.artgallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.artgallery.Model.DBManager;
import com.artgallery.Model.Item;
import com.artgallery.Model.User;
import com.artgallery.Util.Util;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UserRecycleViewAdapter.BuyDialogClicked {

    private static final int CHANGE_PROFILE = 2;
    private static final int UPDATE_WALLET = 4;
    private static final int ITEM_ACTIVITY = 6;
    private TextView wellcome;
    private User user;
    private ImageView userImage;
    private TextView userName;
    private TextView userEmail;
    private TextView about;
    private TextView userWallet;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private View topView;
    private RecyclerView recyclerView;
    private ArrayList<Item> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("USER");

        wellcome = (TextView) findViewById(R.id.user_text_view_wellcome);

        NavigationView userHeader = (NavigationView) findViewById(R.id.nav_view);
        View v = userHeader.getHeaderView(0);
        userName = (TextView) v.findViewById(R.id.nav_header_text_view_user_name);
        userEmail = (TextView) v.findViewById(R.id.nav_header_text_view_user_email);
        userWallet = (TextView) v.findViewById(R.id.nav_header_text_view_user_wallet);
        userImage = (ImageView) v.findViewById(R.id.nav_header_image);
        topView = findViewById(R.id.content_user_parent_layout);
        recyclerView = (RecyclerView) findViewById(R.id.content_user_recycler_view);

        if (user.getUserImageBytes() != null) {

            byte[] bytes = user.getUserImageBytes();

            if (bytes == null) {
                userImage.setImageResource(R.mipmap.emptyprofile);
            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                RoundedBitmapDrawable round = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                round.setCircular(true);
                userImage.setImageDrawable(round);
            }
        }


        userName.setText(user.getName());
        if (user.isAdmin()) {
            userEmail.setText(R.string.you_are_admin);
        } else {
            userEmail.setText(user.getEmail());
        }

        userWallet.setText(getString(R.string.wallet) + String.valueOf(user.getWallet()) + getString(R.string.money));


        topView.requestFocus();
        Util.hideSoftKeyboard(UserActivity.this);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                recyclerView.requestFocus();
                Util.hideSoftKeyboard(UserActivity.this);
                return false;
            }
        });

        topView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                topView.requestFocus();
                Util.hideSoftKeyboard(UserActivity.this);

                return false;
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_photo_black_24dp);

        if (user.getSaleFlag()) {
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getAllItems();
    }


    @Override
    public void onResume() {
        super.onResume();

        getAllItems();

        userName.setText(user.getName());
        userWallet.setText(getString(R.string.wallet) + String.valueOf(user.getWallet()) + getString(R.string.money));
    }


    private void initiatePopupAbout(View v) {

        Util.hideSoftKeyboard(UserActivity.this);

        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.about, null);
        popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(topView, Gravity.CENTER, 0, 0);
        Util.dimBehind(popupWindow);

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder exitAlert = new AlertDialog.Builder(UserActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
            exitAlert.setTitle(R.string.leaving);
            exitAlert.setMessage(R.string.sureToLeaving);
            exitAlert.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            exitAlert.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alert = exitAlert.create();
            alert.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search_view);
        final SearchView search = (SearchView) MenuItemCompat.getActionView(searchItem);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                getItemsBySearchWord(query);

                search.clearFocus();
                search.setQuery("", false);
                MenuItemCompat.collapseActionView(searchItem);
                search.setIconified(true);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_item) {

//            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("USER", user);
//            intent.putExtras(bundle);


            Intent intent = new Intent(UserActivity.this, ItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("USER", user);
            intent.putExtras(bundle);
            startActivityForResult(intent, ITEM_ACTIVITY);
        }

        if (id == R.id.action_wallet) {
            Intent intent = new Intent(UserActivity.this, WalletActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("USER", user);
            intent.putExtras(bundle);
            startActivityForResult(intent, UPDATE_WALLET);
        }

        if (id == R.id.action_profile) {
            Intent intent = new Intent(UserActivity.this, ProfileActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("USER", user);
            intent.putExtras(bundle);
            startActivityForResult(intent, CHANGE_PROFILE);
        }

        if (id == R.id.action_about) {
            initiatePopupAbout(findViewById(R.id.content_user_parent_layout));
        }

        if (id == R.id.action_logout) {
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        Menu m = nv.getMenu();
        int id = item.getItemId();

        if (id == R.id.category_graphics) {
            boolean b = !m.findItem(R.id.graphics_linocut).isVisible();
            Util.magic(m, "graphics", b);
            return true;
        } else if (id == R.id.category_painting) {
            boolean b = !m.findItem(R.id.painting_acrylic_painting).isVisible();
            Util.magic(m, "painting", b);
            return true;
        } else if (id == R.id.category_ceramics) {
            boolean b = !m.findItem(R.id.ceramics_wall_panel).isVisible();
            Util.magic(m, "ceramics", b);
            return true;
        } else if (id == R.id.category_textiles) {
            boolean b = !m.findItem(R.id.textiles_accessories).isVisible();
            Util.magic(m, "textiles", b);
            return true;
        } else if (id == R.id.category_glassware) {
            boolean b = !m.findItem(R.id.glassware_household_glass_sculpture).isVisible();
            Util.magic(m, "glass", b);
            return true;
        } else if (id == R.id.category_iconography) {
            boolean b = !m.findItem(R.id.iconography_new_style).isVisible();
            Util.magic(m, "icon", b);
            return true;
        } else if (id == R.id.category_sculpture) {
            boolean b = !m.findItem(R.id.sculpture_gypsum).isVisible();
            Util.magic(m, "sculpture", b);
            return true;
        } else if (id == R.id.category_wood_carving) {
            boolean b = !m.findItem(R.id.wood_carving_classic).isVisible();
            Util.magic(m, "wood", b);
            return true;
        } else if (id == R.id.graphics_etching) {
            getItemBySubtype("etching");
        } else if (id == R.id.graphics_linocut) {
            getItemBySubtype("linocut");
        } else if (id == R.id.graphics_screen_printing) {
            getItemBySubtype("screen printing");
        } else if (id == R.id.painting_acrylic_painting) {
            getItemBySubtype("acrylic painting");
        } else if (id == R.id.painting_aquarelle) {
            getItemBySubtype("aquarelle");
        } else if (id == R.id.painting_charcoal) {
            getItemBySubtype("charcoal");
        } else if (id == R.id.painting_oil_painting) {
            getItemBySubtype("oil painting");
        } else if (id == R.id.painting_pastel) {
            getItemBySubtype("pastel");
        } else if (id == R.id.painting_pencil_painting) {
            getItemBySubtype("pencil painting");
        } else if (id == R.id.ceramics_household_ceramics) {
            getItemBySubtype("household ceramics");
        } else if (id == R.id.ceramics_souvenir_ceramics) {
            getItemBySubtype("souvenir ceramics");
        } else if (id == R.id.ceramics_wall_panel) {
            getItemBySubtype("wall panel");
        } else if (id == R.id.wood_carving_classic) {
            getItemBySubtype("classic wood carving");
        } else if (id == R.id.wood_carving_interior) {
            getItemBySubtype("interior carving");
        } else if (id == R.id.wood_carving_modern) {
            getItemBySubtype("modern carving");
        } else if (id == R.id.wood_carving_souvenir) {
            getItemBySubtype("souvenir woodcarving");
        } else if (id == R.id.sculpture_gypsum) {
            getItemBySubtype("gypsum");
        } else if (id == R.id.sculpture_metal) {
            getItemBySubtype("metal");
        } else if (id == R.id.sculpture_stone) {
            getItemBySubtype("stone");
        } else if (id == R.id.iconography_new_style) {
            getItemBySubtype("new style");
        } else if (id == R.id.iconography_old_style) {
            getItemBySubtype("old style");
        } else if (id == R.id.glassware_household_glass_sculpture) {
            getItemBySubtype("household glass sculpture");
        } else if (id == R.id.glassware_jewelery) {
            getItemBySubtype("jewelery");
        } else if (id == R.id.glassware_souvenir_glass) {
            getItemBySubtype("souvenir glass");
        } else if (id == R.id.glassware_stained_glass) {
            getItemBySubtype("stained glass");
        } else if (id == R.id.textiles_accessories) {
            getItemBySubtype("accessories");
        } else if (id == R.id.textiles_carpets) {
            getItemBySubtype("carpets");
        } else if (id == R.id.textiles_clothes) {
            getItemBySubtype("clothes");
        } else if (id == R.id.textiles_textile_panel) {
            getItemBySubtype("textile panel");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHANGE_PROFILE && resultCode == RESULT_OK) {

            user = (User) data.getSerializableExtra("USER");
            userName = (TextView) findViewById(R.id.nav_header_text_view_user_name);
            userName.setText(user.getName());

            byte[] bytes = user.getUserImageBytes();
            if (bytes == null) {
                userImage.setImageResource(R.mipmap.emptyprofile);
            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                RoundedBitmapDrawable round = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                round.setCircular(true);

                userImage.setImageDrawable(round);
            }
        }

        if (requestCode == UPDATE_WALLET && resultCode == RESULT_OK) {
            user.setWallet(DBManager.getInstance(this).getUserWallet(user.getId()));
            userWallet.setText(getString(R.string.wallet) + String.valueOf(user.getWallet()) + getString(R.string.money));
            Toast.makeText(this, R.string.wallet_updated, Toast.LENGTH_SHORT).show();
        }

        if (requestCode == ITEM_ACTIVITY && resultCode == RESULT_OK) {

            user = (User) data.getSerializableExtra("USER");
        }
    }


    private void getItemBySubtype(String subtype) {

        items = DBManager.getInstance(this).getItemsBySubtype(subtype, user.getId());

        if (items.size() == 0 || items.isEmpty()) {
            setWellcomeTextVisible();
            wellcome.setText(R.string.sorry_no_items);
        } else {
            setRecyclerViewVisible();
        }
    }


    private void getAllItems() {

        items = DBManager.getInstance(this).getAllItems(user.getId());

        if (items.size() == 0 || items.isEmpty()) {
            setWellcomeTextVisible();
            wellcome.setText(R.string.no_items_for_sale);
        } else {
            setRecyclerViewVisible();
        }
    }


    private void getItemsBySearchWord(String search) {
        items = DBManager.getInstance(this).getItemsBySearchWord(search, user.getId());

        if (items.size() == 0 || items.isEmpty()) {
            setWellcomeTextVisible();
            wellcome.setText(R.string.no_searched_items);
        } else {
            setRecyclerViewVisible();
        }

    }


    private void setWellcomeTextVisible() {

        wellcome.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }


    private void setRecyclerViewVisible() {

        recyclerView.setVisibility(View.VISIBLE);
        wellcome.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserRecycleViewAdapter(this, items, user));
    }


    @Override
    public void newWalletSum() {

        userWallet.setText(getString(R.string.wallet) + String.valueOf(user.getWallet()) + getString(R.string.money));

        recyclerView.setVisibility(View.GONE);
        wellcome.setVisibility(View.VISIBLE);
        wellcome.setText(getString(R.string.you_have_in_your_wallet) + String.valueOf(user.getWallet()) + getString(R.string.you_have_in_your_wallet_end));
    }
}
