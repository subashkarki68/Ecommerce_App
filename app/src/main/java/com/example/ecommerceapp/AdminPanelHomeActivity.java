package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ecommerceapp.databinding.ActivityAdminPanelHomeBinding;

public class AdminPanelHomeActivity extends AppCompatActivity {

    ActivityAdminPanelHomeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAdminPanelHomeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }
}