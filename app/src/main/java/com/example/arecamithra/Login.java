package com.example.arecamithra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;

public class Login extends AppCompatActivity {
    Button signup,login;
    String encodedImage;
    TextInputLayout reg_username,reg_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.app_background, getTheme()));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);

        reg_username=findViewById(R.id.reg_username);
        reg_password=findViewById(R.id.reg_password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s=new Intent(Login.this,SignUp.class);
                startActivity(s);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword()){
                    return;
                }else{
                    isUser();
                }

            }
        });




    }

    private void isUser() {
        String userEnteredUsername= reg_username.getEditText().getText().toString().trim();
        String userEnteredPassword= reg_password.getEditText().getText().toString().trim();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkUser=reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    reg_username.setError(null);
                    reg_username.setErrorEnabled(false);

                    String passwordFromDB=snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if(passwordFromDB.equals(userEnteredPassword)){
                        reg_username.setError(null);
                        reg_username.setErrorEnabled(false);

                        String nameFromDB=snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB=snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNumberFromDB=snapshot.child(userEnteredUsername).child("phoneNumber").getValue(String.class);
                        String emailFromDB=snapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        String imageFromDB=snapshot.child(userEnteredUsername).child("imageUrl").getValue(String.class);
                        encodedImage=" ";

                        SessionManagerWeather sessionManagerWeather=new SessionManagerWeather(Login.this);
                        sessionManagerWeather.createWeatherSession(" "," "," "," "," ");
                       if(!imageFromDB.equalsIgnoreCase(" ")) {
                           Picasso.get().load(imageFromDB).into(new Target() {
                               @Override
                               public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                   Bitmap bitmapImage = bitmap;
                                   ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                   bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                   byte[] b = baos.toByteArray();
                                   encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                                   Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                                   SessionManagerUser sessionManagerUser = new SessionManagerUser(Login.this);
                                   sessionManagerUser.createLoginSession(nameFromDB, usernameFromDB, emailFromDB, passwordFromDB, phoneNumberFromDB, encodedImage);
                                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                   startActivity(intent);
                               }

                               @Override
                               public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                   Toast.makeText(Login.this, "Error Occured", Toast.LENGTH_SHORT).show();
                               }

                               @Override
                               public void onPrepareLoad(Drawable placeHolderDrawable) {
                                   Toast.makeText(Login.this, "Loading", Toast.LENGTH_SHORT).show();
                               }
                           });
                       }else {
                           SessionManagerUser sessionManagerUser = new SessionManagerUser(Login.this);
                           sessionManagerUser.createLoginSession(nameFromDB, usernameFromDB, emailFromDB, passwordFromDB, phoneNumberFromDB, encodedImage);
                           Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                           startActivity(intent);
                       }
                    }else{
                        reg_password.setError("Wrong Password");
                        reg_password.requestFocus();
                    }
                }
                else{
                    reg_username.setError("no such User exist");
                    reg_username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private Boolean validateEmail() {
        String val = reg_username.getEditText().getText().toString();

        if (val.isEmpty()) {
            reg_username.setError("Field cannot be empty");
            return false;
        }  else {
            reg_username.setError(null);
            reg_username.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword() {
        String val = reg_password.getEditText().getText().toString();


        if (val.isEmpty()) {
            reg_password.setError("Field cannot be empty");
            return false;
        }  else {
            reg_password.setError(null);
            reg_password.setErrorEnabled(false);
            return true;
        }

    }
}