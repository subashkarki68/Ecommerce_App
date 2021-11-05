package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecommerceapp.databinding.ActivityMainBinding;
import com.example.ecommerceapp.prevalent.UserCookie;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;

    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
//      setContentView(R.layout.activity_main);
        setContentView(mBinding.getRoot());

        //Check Previous Remember me Value
        mSharedPreferences = getSharedPreferences(UserCookie.ACCOUNT_SETTINGS_SHARED_PREFERENCES,MODE_PRIVATE);
        String rememberMeValue = mSharedPreferences.getString(UserCookie.REMEMBER_ME_SHARED_PREFERENCE_KEY,"");
        if(rememberMeValue.equals("true")){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "Please Sign in", Toast.LENGTH_SHORT).show();
        }


        mBinding.mainLoginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        mBinding.mainJoinBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}