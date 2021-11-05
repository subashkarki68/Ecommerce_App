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

import com.example.ecommerceapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding mBinding;

    private ProgressDialog mLoadingbar;
    private String mName, mPhoneNumber, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mLoadingbar = new ProgressDialog(this);

        mBinding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        mName = mBinding.registerFullNameInput.getText().toString().trim();
        mPhoneNumber = mBinding.registerPhoneNumberInput.getText().toString();
        mPassword = mBinding.registerPasswordInput.getText().toString();
        boolean accountInfoPassed = true;

        if (TextUtils.isEmpty(mName)) {
            mBinding.registerFullNameInput.setError("Name Can't be empty");
            accountInfoPassed = false;
        }
        if (TextUtils.isEmpty(mPhoneNumber)) {
            mBinding.registerPhoneNumberInput.setError("Please, Enter your Phone Number");
            accountInfoPassed = false;
        } else if (mPhoneNumber.length() < 10) {
            mBinding.registerPhoneNumberInput.setError("Please, enter 10 digit number");
            accountInfoPassed = false;
        }
        if (TextUtils.isEmpty(mPassword) || mPassword.length() < 8) {
            mBinding.registerPasswordInput.setError("Please, Enter 8 digit password");
            accountInfoPassed = false;
        }
        if (accountInfoPassed)
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
                if (!(snapshot.child("Users").child(phoneNumber).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();

                    userdataMap.put("phoneNumber", phoneNumber);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);

                    databaseReference.child("Users").child(phoneNumber).updateChildren(userdataMap)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                                    mLoadingbar.dismiss();

                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Please Check Your Connection!!!", Toast.LENGTH_SHORT).show();
                                    mLoadingbar.dismiss();
                                }
                            });

                } else {
                    Toast.makeText(RegisterActivity.this, "The number " + phoneNumber + " is already registered.", Toast.LENGTH_SHORT).show();
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