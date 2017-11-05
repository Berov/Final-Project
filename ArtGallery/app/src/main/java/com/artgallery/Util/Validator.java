package com.artgallery.Util;

import android.util.Patterns;

/**
 * Created by Berov on 28.10.2017 г..
 */

public class Validator {

    public static boolean isValidName(String name) {

        String pattern = "[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я ][a-zA-Zа-яА-Я][a-zA-Zа-яА-Я ]*";
        return name.matches(pattern);
    }

    public static boolean isValidPassword(String password) {

        String pattern = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}";
        return password.matches(pattern);
    }

    public static boolean isValidEmail(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


//    public static boolean isValidPhoneNumber(String phoneNumber) {
//        return Patterns.PHONE.matcher(phoneNumber).matches();
//    }


    public static boolean isValidPhoneNumber(String number) {

        String pattern = "^[+]?[0-9]{10,13}$";
        return number.matches(pattern);
    }
}
