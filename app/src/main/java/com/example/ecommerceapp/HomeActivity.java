package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.ecommerceapp.model.Users;

public class HomeActivity extends AppCompatActivity {

    //For Shared Preferences
    final String ACCOUNT_SETTINGS_SHARED_PREFERENCES = "Account_shared_preferences";
    final String REMEMBER_ME_SHARED_PREFERENCE_KEY = "Remember_me_key";
    final String USER_DETAIL_SHARED_PREFERENCE_KEY = "User_details_shared_preferences";

    private Users mUser;
    private SharedPreferences mSharedPreferences;

    AppCompatButton logoutBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBTN = findViewById(R.id.home_logoutBTN);

        //Save User Details in User class
        //SharedPreference
        mSharedPreferences = getSharedPreferences(ACCOUNT_SETTINGS_SHARED_PREFERENCES,MODE_PRIVATE);
        //................................//
        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(ACCOUNT_SETTINGS_SHARED_PREFERENCES,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(REMEMBER_ME_SHARED_PREFERENCE_KEY,"false");
                editor.apply();

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}