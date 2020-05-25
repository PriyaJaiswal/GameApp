package com.app.nggames.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pspl on 2/19/2018.
 */

public class SharedPref {

    private static SharedPreferences sharedpreferences;
    public static final String NGGAME = "NGGAME";
    public static final String TOKEN = "TOKEN";
    public static final String TOKENTYPE = "TOKENTYPE";
    public static final String EXPIRESAT = "EXPIRESAT";
    public static final String USERLOGEDIN = "USER_LOGEDIN";
    public static final String LOGGEDINSTATUS = "LOGGED_OUT";
    public static final String EMAILID = "EMAILID";
    public static final String MOBILENO = "MOBILENO";
    public static final String PASSWORD = "PASSWORD";
    public static final String FNAME = "FNAME";
    public static final String LNAME = "LNAME";


    public static void putSharedPreferenceForString(Context context, String key, String addingParameter) {
        sharedpreferences = context.getSharedPreferences(NGGAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, addingParameter);
        editor.apply();
    }

    public static String getSharedPreferenceForString(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(NGGAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "ON");
    }


    public static void putSharedPreferenceForBoolean(Context context, String key, boolean addingParameter) {
        sharedpreferences = context.getSharedPreferences(NGGAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, addingParameter);
        editor.apply();
    }


    public static boolean getSharedPreferenceForBoolean(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(NGGAME, Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(key, false);
    }

}
