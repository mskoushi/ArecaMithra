package com.example.arecamithra;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    Button update_profile;
    private Uri imagePath;
    TextView label_fullName, label_username;
    CircleImageView profileImageUpload;
    TextInputLayout fullName, email, password, phoneNumber;
    DatabaseReference reference;
    SessionManagerUser sessionManagerUser;
    HashMap<String, String> userDetails;
    String fullName_s, username_s, email_s, password_s, phoneNumber_s,image;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageUpload = view.findViewById(R.id.profile_image);

        update_profile = view.findViewById(R.id.update_profile);

        label_fullName = view.findViewById(R.id.label_fullName);
        label_username = view.findViewById(R.id.label_username);
        fullName = view.findViewById(R.id.fullName);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        phoneNumber = view.findViewById(R.id.phoneNumber);

        sessionManagerUser = new SessionManagerUser(getActivity());
        userDetails = sessionManagerUser.getUserDetailFromSession();
        fullName_s = userDetails.get(SessionManagerUser.KEY_FULLNAME);
        username_s = userDetails.get(SessionManagerUser.KEY_USERNAME);
        email_s = userDetails.get(SessionManagerUser.KEY_EMAIL);
        password_s = userDetails.get(SessionManagerUser.KEY_PASSWORD);
        phoneNumber_s = userDetails.get(SessionManagerUser.KEY_PHONENUMBER);
        image=userDetails.get(SessionManagerUser.KEY_IMAGE);


        label_fullName.setText(fullName_s);
        label_username.setText(username_s);
        fullName.getEditText().setText(fullName_s);
        email.getEditText().setText(email_s);
        password.getEditText().setText(password_s);
        phoneNumber.getEditText().setText(phoneNumber_s);
        if(!image.equalsIgnoreCase(" ")){
            byte[] b= Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(b,0,b.length);
            profileImageUpload.setImageBitmap(bitmap);
        }

        reference = FirebaseDatabase.getInstance().getReference("users");


        profileImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,1);
            }
        });




        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag = 0;
                if (isfullNameChanged()) {
                    flag = 1;
                }
                if (isEmailChanged()) {
                    flag = 1;
                }
                if (isPasswordChanged()) {
                    flag = 1;
                }
                if (isPhoneNumberChanged()) {
                    flag = 1;
                }
                if (flag == 1) {
                    Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getActivity(), "Data is same and can not be updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK && data!=null) {
            imagePath = data.getData();
            getImageInImageView();

            ProgressDialog progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading.....");
            progressDialog.show();

            FirebaseStorage.getInstance().getReference("images/"+username_s+ UUID.randomUUID().toString()).putFile(imagePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                       task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                           @Override
                           public void onComplete(@NonNull Task<Uri> task) {
                               if(task.isSuccessful()){
                                   updateProfilePicture(task.getResult().toString());
                               }
                           }
                       });
                        Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress=100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded"+(int)progress+"%");
                }
            });
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void updateProfilePicture(String url) {
        reference.child(username_s).child("imageUrl").setValue(url);
    }



    private void getImageInImageView() {
        Bitmap bitmap= null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileImageUpload.setImageBitmap(bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        sessionManagerUser.updateImage(encodedImage);
    }

    private boolean isfullNameChanged() {
        if (!fullName_s.equals(fullName.getEditText().getText().toString())) {
            reference.child(username_s).child("name").setValue(fullName.getEditText().getText().toString());
            //fullName_s=fullName.getEditText().getText().toString();
            sessionManagerUser.updateFullName(fullName.getEditText().getText().toString());
            label_fullName.setText(fullName.getEditText().getText().toString());
            fullName.getEditText().setText(fullName.getEditText().getText().toString());
            fullName_s = fullName.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailChanged() {
        if (!email_s.equals(email.getEditText().getText().toString())) {
            reference.child(username_s).child("email").setValue(email.getEditText().getText().toString());
            //fullName_s=fullName.getEditText().getText().toString();
            sessionManagerUser.updateEmail(email.getEditText().getText().toString());

            email.getEditText().setText(email.getEditText().getText().toString());
            email_s = email.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isPasswordChanged() {
        if (!password_s.equals(password.getEditText().getText().toString())) {
            reference.child(username_s).child("password").setValue(password.getEditText().getText().toString());
            //fullName_s=fullName.getEditText().getText().toString();
            sessionManagerUser.updatePassword(password.getEditText().getText().toString());
            password.getEditText().setText(password.getEditText().getText().toString());
            password_s = password.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isPhoneNumberChanged() {
        if (!phoneNumber_s.equals(phoneNumber.getEditText().getText().toString())) {
            reference.child(username_s).child("phoneNumber").setValue(phoneNumber.getEditText().getText().toString());
            //fullName_s=fullName.getEditText().getText().toString();
            sessionManagerUser.updatePhoneNumber(phoneNumber.getEditText().getText().toString());
            phoneNumber.getEditText().setText(phoneNumber.getEditText().getText().toString());
            phoneNumber_s = phoneNumber.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }
}