package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ecommerceapp.databinding.ActivityAdminPanelAddNewProductBinding;

public class AdminPanelAddNewProductActivity extends AppCompatActivity {

    ActivityAdminPanelAddNewProductBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAdminPanelAddNewProductBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
//      setContentView(R.layout.activity_admin_panel_add_new_product);
    }
}