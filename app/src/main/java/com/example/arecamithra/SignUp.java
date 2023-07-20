package com.example.arecamithra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    Button signup, login;
    TextInputLayout reg_name, reg_email, reg_password, reg_phoneNumber,reg_username;

    //Firebase
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.app_background, getTheme()));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);

        reg_name = findViewById(R.id.reg_name);
        reg_email = findViewById(R.id.reg_email);
        reg_password = findViewById(R.id.reg_password);
        reg_phoneNumber = findViewById(R.id.reg_phoneNumber);
        reg_username = findViewById(R.id.reg_username);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateName() |!validateUsername()| !validateEmail() | !validatePassword() | !validatePhoneNumber()) {
                    return;
                }
                String name = reg_name.getEditText().getText().toString();
                String username = reg_username.getEditText().getText().toString();
                String email = reg_email.getEditText().getText().toString();
                String password = reg_password.getEditText().getText().toString();
                String phoneNumber = reg_phoneNumber.getEditText().getText().toString();




                UserHelperClass helperClass = new UserHelperClass(name,username,email, password, phoneNumber);

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                Query checkUserName=reference.orderByChild("username").equalTo(username);
                checkUserName.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            reg_username.setError("Already this username exist! Try different one");
                        }else {
                            reg_username.setError(null);
                            reg_username.setErrorEnabled(false);
                            reference.child(username).setValue(helperClass);
                            SessionManagerUser sessionManagerUser=new SessionManagerUser(SignUp.this);
                            sessionManagerUser.createLoginSession(name,username,email,password,phoneNumber," ");
                            Intent s = new Intent(SignUp.this, MainActivity.class);
                            startActivity(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(SignUp.this, Login.class);
                startActivity(l);
            }
        });

    }

    //Form validation
    private Boolean validateName() {
        String val = reg_name.getEditText().getText().toString();
        if (val.isEmpty()) {
            reg_name.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            reg_name.setError("Username too long");
            return false;
        } else {
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }

    }
    private Boolean validateUsername() {
        String val = reg_username.getEditText().getText().toString();
        if (val.isEmpty()) {
            reg_name.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            reg_name.setError("Username too long");
            return false;
        } else {
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmail() {
        String val = reg_email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            reg_email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            reg_email.setError("Invalid email address");
            return false;
        } else {
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword() {
        String val = reg_password.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +  //at least 1 digit
                "(?=.*[a-z])" +  //at least 1 lower case letter
                "(?=.*[A-Z])" +  //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&+=])" + // at least one special character
               // "(?=\\s+$])" +  //no white spaces
                ".{4,}" +  //at least 4 characters
                "$";

        if (val.isEmpty()) {
            reg_password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            reg_password.setError("Password is too weak");
            return false;
        } else {
            reg_password.setError(null);
            reg_password.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePhoneNumber() {
        String val = reg_phoneNumber.getEditText().getText().toString();
        if (val.isEmpty()) {
            reg_phoneNumber.setError("Field cannot be empty");
            return false;
        } else {
            reg_phoneNumber.setError(null);
            reg_phoneNumber.setErrorEnabled(false);
            return true;
        }

    }
}