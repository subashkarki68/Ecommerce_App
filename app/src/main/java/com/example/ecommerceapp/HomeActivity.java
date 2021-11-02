package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.ecommerceapp.model.Users;
import com.example.ecommerceapp.prevalent.UserCookie;

public class HomeActivity extends AppCompatActivity {

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
        mSharedPreferences = getSharedPreferences(UserCookie.ACCOUNT_SETTINGS_SHARED_PREFERENCES,MODE_PRIVATE);
        //................................//
        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(UserCookie.ACCOUNT_SETTINGS_SHARED_PREFERENCES,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(UserCookie.REMEMBER_ME_SHARED_PREFERENCE_KEY,"false");
                editor.apply();

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}