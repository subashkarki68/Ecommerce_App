package com.example.ecommerceapp.prevalent;

import com.example.ecommerceapp.model.Users;

public class UserCookie {
    private static Users currentOnlineUser;
    //For Shared Preferences
    public static final String ACCOUNT_SETTINGS_SHARED_PREFERENCES = "Account_shared_preferences";
    public static final String REMEMBER_ME_SHARED_PREFERENCE_KEY = "Remember_me_key";
    public static final String USER_DETAIL_SHARED_PREFERENCE_KEY = "User_details_shared_preferences";
}
