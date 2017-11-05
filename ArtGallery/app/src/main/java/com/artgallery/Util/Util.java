package com.artgallery.Util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import com.artgallery.Model.DBManager;
import com.artgallery.Model.User;
import com.artgallery.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Berov on 28.10.2017 г..
 */

public class Util {

    public static int GALLERY_PERCENT = 20;

    public static void addGalleryMoney(double money, Context context) {

        User admin = DBManager.getInstance(context).getUserByID(1); //static added admins ID
        admin.setWallet(twoDecimalPlaces(admin.getWallet() + money));
        DBManager.getInstance(context).updateUser(admin);
    }


    public static double twoDecimalPlaces(double money) {

        return (double) Math.round(money * 100) / 100;
    }


    public static int getScreenWidth() {

        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }


    public static int getScreenHeight() {

        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);

        return outputStream.toByteArray();
    }

    public static void dimBehind(PopupWindow popupWindow) {

        View container;

        if (popupWindow.getBackground() == null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent();

            } else {
                container = popupWindow.getContentView();
            }

        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();

            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }

        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();

        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
    }


    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public static void magic(Menu m, String b, boolean state) {

        m.findItem(R.id.graphics_linocut).setVisible(b.equals("graphics") && state);
        m.findItem(R.id.graphics_screen_printing).setVisible(b.equals("graphics") && state);
        m.findItem(R.id.graphics_etching).setVisible(b.equals("graphics") && state);
        m.findItem(R.id.painting_acrylic_painting).setVisible(b.equals("painting") && state);
        m.findItem(R.id.painting_aquarelle).setVisible(b.equals("painting") && state);
        m.findItem(R.id.painting_charcoal).setVisible(b.equals("painting") && state);
        m.findItem(R.id.painting_oil_painting).setVisible(b.equals("painting") && state);
        m.findItem(R.id.painting_pastel).setVisible(b.equals("painting") && state);
        m.findItem(R.id.painting_pencil_painting).setVisible(b.equals("painting") && state);
        m.findItem(R.id.ceramics_wall_panel).setVisible(b.equals("ceramics") && state);
        m.findItem(R.id.ceramics_souvenir_ceramics).setVisible(b.equals("ceramics") && state);
        m.findItem(R.id.ceramics_household_ceramics).setVisible(b.equals("ceramics") && state);
        m.findItem(R.id.textiles_accessories).setVisible(b.equals("textiles") && state);
        m.findItem(R.id.textiles_carpets).setVisible(b.equals("textiles") && state);
        m.findItem(R.id.textiles_clothes).setVisible(b.equals("textiles") && state);
        m.findItem(R.id.textiles_textile_panel).setVisible(b.equals("textiles") && state);
        m.findItem(R.id.glassware_household_glass_sculpture).setVisible(b.equals("glass") && state);
        m.findItem(R.id.glassware_jewelery).setVisible(b.equals("glass") && state);
        m.findItem(R.id.glassware_souvenir_glass).setVisible(b.equals("glass") && state);
        m.findItem(R.id.glassware_stained_glass).setVisible(b.equals("glass") && state);
        m.findItem(R.id.iconography_new_style).setVisible(b.equals("icon") && state);
        m.findItem(R.id.iconography_old_style).setVisible(b.equals("icon") && state);
        m.findItem(R.id.sculpture_gypsum).setVisible(b.equals("sculpture") && state);
        m.findItem(R.id.sculpture_metal).setVisible(b.equals("sculpture") && state);
        m.findItem(R.id.sculpture_stone).setVisible(b.equals("sculpture") && state);
        m.findItem(R.id.wood_carving_classic).setVisible(b.equals("wood") && state);
        m.findItem(R.id.wood_carving_interior).setVisible(b.equals("wood") && state);
        m.findItem(R.id.wood_carving_modern).setVisible(b.equals("wood") && state);
        m.findItem(R.id.wood_carving_souvenir).setVisible(b.equals("wood") && state);
    }
}
