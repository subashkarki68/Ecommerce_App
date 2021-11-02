package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecommerceapp.prevalent.UserCookie;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton LoginButton;
    private AppCompatButton JoinNowButton;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginButton = findViewById(R.id.main_login_btn);
        JoinNowButton = findViewById(R.id.main_join_btn);

        //Check Previous Remember me Value
        mSharedPreferences = getSharedPreferences(UserCookie.ACCOUNT_SETTINGS_SHARED_PREFERENCES,MODE_PRIVATE);
        String rememberMeValue = mSharedPreferences.getString(UserCookie.REMEMBER_ME_SHARED_PREFERENCE_KEY,"");
        if(rememberMeValue.equals("true")){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "Please Sign in", Toast.LENGTH_SHORT).show();
        }


        LoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        JoinNowButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}