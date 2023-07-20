package com.example.arecamithra;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.util.HashMap;

public class SessionManagerUser {
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public  static final String KEY_IMAGE="image";

    public SessionManagerUser(Context _context) {
        context = _context;
        usersSession = _context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }

    public void createLoginSession(String fullName, String username, String email, String password, String phoneNumber, String image) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_PHONENUMBER, phoneNumber);
       editor.putString(KEY_IMAGE,image);
        editor.commit();
    }
    public void updateLogin(boolean flag){
        editor.putBoolean(IS_LOGIN, flag);
        editor.commit();
    }
    public void updateFullName(String fullname){
        editor.putString(KEY_FULLNAME,fullname);
        editor.commit();
    }
    public void updateEmail(String email){
        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }
    public void updatePassword(String password){
        editor.putString(KEY_PASSWORD,password);
        editor.commit();
    }
    public void updatePhoneNumber(String phoneNumber){
        editor.putString(KEY_PHONENUMBER,phoneNumber);
        editor.commit();
    }
    public  void updateImage(String image){
        editor.putString(KEY_IMAGE,image);
        editor.commit();
    }

    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_FULLNAME, usersSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, usersSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, usersSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_PHONENUMBER, usersSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_IMAGE,usersSession.getString(KEY_IMAGE,null));
        return userData;
    }

    public boolean checkLogin() {
        if (usersSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }


}
