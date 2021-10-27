package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private AppCompatButton mLoginButton;
    private EditText mInputPhoneNumber, mInputPassword;
    private String mPhoneNumber, mPassword;
    private ProgressDialog mLoadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = findViewById(R.id.login_btn);
        mInputPhoneNumber = findViewById(R.id.login_phoneNumberInput);
        mInputPassword = findViewById(R.id.login_passwordInput);
        mLoadingbar = new ProgressDialog(this);
        
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin();
            }
        });
    }

    private void tryLogin() {
        mPhoneNumber = mInputPhoneNumber.getText().toString();
        mPassword = mInputPassword.getText().toString();
        boolean accountInfoPassed = true;

        if (TextUtils.isEmpty(mPhoneNumber)) {
            mInputPhoneNumber.setError("Please, Enter your Phone Number");
            accountInfoPassed = false;
        } else if (mPhoneNumber.length() < 10) {
            mInputPhoneNumber.setError("Please, enter 10 digit number");
            accountInfoPassed = false;
        }
        if (TextUtils.isEmpty(mPassword) || mPassword.length() < 8) {
            mInputPassword.setError("Please, Enter 8 digit password");
            accountInfoPassed = false;
        }
        if (accountInfoPassed) {
            mLoadingbar.setTitle("Logging in");
            mLoadingbar.setMessage("Trying to Logging you in...");
            mLoadingbar.setCancelable(false);
            mLoadingbar.show();
            proceedToLogin();
        }
    }

    private void proceedToLogin() {
        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(mPhoneNumber).exists()){
                    Users user = snapshot.child("Users").child(mPhoneNumber).getValue(Users.class);

                    if (user != null) {
                        if(user.getPhoneNumber().equals(mPhoneNumber)){
                            if(user.getPassword().equals(mPassword)){

                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                mLoadingbar.dismiss();

                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intent);

                            }else{
                                mInputPassword.setError("Password is Incorrect");
                                mLoadingbar.dismiss();
                            }
                        }
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Account " + mPhoneNumber + " Doesn't Exists.", Toast.LENGTH_SHORT).show();
                    mLoadingbar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}