package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.databinding.ActivityLoginBinding;
import com.example.ecommerceapp.model.Users;
import com.example.ecommerceapp.prevalent.UserCookie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding mBinding;

    private String mPhoneNumber, mPassword;
    private ProgressDialog mLoadingbar;
    private SharedPreferences mSharedPreferences;
    private Dialog mAdminLoginDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mLoadingbar = new ProgressDialog(this);
        mSharedPreferences = getSharedPreferences(UserCookie.ACCOUNT_SETTINGS_SHARED_PREFERENCES,MODE_PRIVATE);

        //Loading Bar
        mLoadingbar.setTitle("Logging in");
        mLoadingbar.setMessage("Trying to Logging you in...");
        mLoadingbar.setCancelable(false);

        //Check Previous Remember me Value
        mSharedPreferences = getSharedPreferences(UserCookie.ACCOUNT_SETTINGS_SHARED_PREFERENCES,MODE_PRIVATE);
        String rememberMeValue = mSharedPreferences.getString(UserCookie.REMEMBER_ME_SHARED_PREFERENCE_KEY,"");
        if(rememberMeValue.equals("true")){
            //Loading bar
            mLoadingbar.show();
            //Retrive User Details
            Gson gson = new Gson();
            String json = mSharedPreferences.getString(UserCookie.USER_DETAIL_SHARED_PREFERENCE_KEY,"");
            Users user = gson.fromJson(json,Users.class);
            mPhoneNumber = user.getPhoneNumber();
            mPassword = user.getPassword();
            proceedToLogin(mPhoneNumber,mPassword);
        }else{
            Toast.makeText(LoginActivity.this, "Not Logged in", Toast.LENGTH_SHORT).show();
        }

        mBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin();
            }
        });

        mBinding.loginAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAdminLoginDialog();
            }
        });

        mBinding.loginRememberMeChkb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked()){
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(UserCookie.REMEMBER_ME_SHARED_PREFERENCE_KEY,"true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                }else if(!buttonView.isChecked()){
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(UserCookie.REMEMBER_ME_SHARED_PREFERENCE_KEY,"false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void callAdminLoginDialog() {
        mAdminLoginDialog = new Dialog(this);
        mAdminLoginDialog.setContentView(R.layout.activity_admin_login_form);
        mAdminLoginDialog.setCancelable(true);
        mAdminLoginDialog.show();
        Window window = mAdminLoginDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        AppCompatButton adminLogin = mAdminLoginDialog.findViewById(R.id.adminLoginDialog_loginBtn);
        EditText phoneNumber = mAdminLoginDialog.findViewById(R.id.adminLoginDialog_phoneNumberInput);
        EditText password = mAdminLoginDialog.findViewById(R.id.adminLoginDialog_passwordInput);
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNumber.getText().toString().equals("9800")
                        && password.getText().toString().equals("admin")){
                    Intent intent = new Intent(LoginActivity.this,AdminPanelHomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }else{
                    Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void tryLogin() {
        mPhoneNumber = mBinding.loginPhoneNumberInput.getText().toString();
        mPassword = mBinding.loginPasswordInput.getText().toString();
        boolean accountInfoPassed = true;

        if (TextUtils.isEmpty(mPhoneNumber)) {
            mBinding.loginPhoneNumberInput.setError("Please, Enter your Phone Number");
            accountInfoPassed = false;
        } else if (mPhoneNumber.length() < 10) {
            mBinding.loginPhoneNumberInput.setError("Please, enter 10 digit number");
            accountInfoPassed = false;
        }
        if (TextUtils.isEmpty(mPassword) || mPassword.length() < 8) {
            mBinding.loginPasswordInput.setError("Please, Enter 8 digit password");
            accountInfoPassed = false;
        }
        if (accountInfoPassed) {
            mLoadingbar.show();
            proceedToLogin(mPhoneNumber,mPassword);
        }
    }

    private void proceedToLogin(String phoneNumber, String password) {
        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(phoneNumber).exists()){
                    Users user = snapshot.child("Users").child(phoneNumber).getValue(Users.class);

                    if (user != null) {
                        if(user.getPhoneNumber().equals(phoneNumber)){
                            if(user.getPassword().equals(password)){

                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                mLoadingbar.dismiss();

                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intent);

                                putLoginCredentialsInSharedPreferences(user);
                                finishAffinity();

                            }else{
                                mBinding.loginPasswordInput.setError("Password is Incorrect");
                                mLoadingbar.dismiss();
                            }
                        }
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Account " + phoneNumber + " Doesn't Exists.", Toast.LENGTH_SHORT).show();
                    mLoadingbar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void putLoginCredentialsInSharedPreferences(Users user) {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(UserCookie.USER_DETAIL_SHARED_PREFERENCE_KEY,json);
        editor.commit();

    }
}