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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private AppCompatButton mCreateAccountButton;
    private EditText mInputName, mInputPhoneNumber, mInputPassword;
    private ProgressDialog mLoadingbar;
    private String mName, mPhoneNumber, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mCreateAccountButton = findViewById(R.id.register_btn);
        mInputName = findViewById(R.id.register_full_nameInput);
        mInputPhoneNumber = findViewById(R.id.register_phoneNumberInput);
        mInputPassword = findViewById(R.id.register_passwordInput);
        mLoadingbar = new ProgressDialog(this);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        mName = mInputName.getText().toString();
        mPhoneNumber = mInputPhoneNumber.getText().toString();
        mPassword = mInputPassword.getText().toString();
        Boolean accountInfoPassed = true;

        if (TextUtils.isEmpty(mName)) {
            mInputName.setError("Name Can't be empty");
            accountInfoPassed = false;
        }
        if (TextUtils.isEmpty(mPhoneNumber)) {
            mInputPhoneNumber.setError("Please, Enter your Phone Number");
            accountInfoPassed = false;
        }
        else if (mPhoneNumber.length() < 10) {
            mInputPhoneNumber.setError("Please, enter 10 digit number");
            accountInfoPassed = false;
        }
        if (TextUtils.isEmpty(mPassword) || mPassword.length() < 8) {
            mInputPassword.setError("Please, Enter 8 digit password");
            accountInfoPassed = false;
        }
        if(accountInfoPassed)
            proceedSignUp();
    }

    private void proceedSignUp() {
        mLoadingbar.setTitle("Creating Account");
        mLoadingbar.setMessage("Please wait while we create your account.");
        mLoadingbar.setCancelable(false);
        mLoadingbar.show();
        validatePhoneNumber(mName, mPhoneNumber, mPassword);
    }

    private void validatePhoneNumber(String name, String phoneNumber, String password) {
        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(phoneNumber).exists())){

                }else{
                    Toast.makeText(RegisterActivity.this, "The number " + phoneNumber + "is already registered.", Toast.LENGTH_SHORT).show();
                    mLoadingbar.dismiss();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}