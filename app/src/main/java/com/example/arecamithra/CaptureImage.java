package com.example.arecamithra;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.arecamithra.ml.ModelUpdatedversion;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CaptureImage extends AppCompatActivity {
CardView camera,gallery;
ImageView back_button;
Bitmap image,setimage;
ImageView imageView;
CardView cards;
TextView result,remedies;
TextView example;
Button remedies_more;
    Intent more;
int imageSize=224;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.app_background, getTheme()));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        camera=findViewById(R.id.camera_btn);
        gallery=findViewById(R.id.gallery_btn);

        cards=findViewById(R.id.image_card);
        imageView=cards.findViewById(R.id.photo);
        result=findViewById(R.id.disease_name);
        remedies=findViewById(R.id.remidies);
        remedies_more=findViewById(R.id.remedies_more);

        example=findViewById(R.id.testout);


        back_button=findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String[] solutions={"Collection and burring of diseased leaves should be strictly followed to reduce the inoculum load.","Collection and burring of diseased leaves should be strictly followed to reduce the inoculum load.","Collection and burring of diseased leaves should be strictly followed to reduce the inoculum load."," "," ","Removal of resinous exudation/sawdust frass and injection of Chlorpyriphos 20EC@ 5ml/litre in bored holes. Cover the injected holes with wet soil to improve the efficiency of treatment.","Trenching of 30 cm width and 60 cm depth around the diseased\n palm and drenching with fungicides will help to arrest the further spread of the disease.","Field sanitation practices include removal and burning of infected  dried bunches to reduce the inoculum level in the garden should be strictly followed."," "," "," ","Application of neem oil emulsion (5 ml/litre water) two-times at 15 days intervals","Bud rot can be managed if the affected palms are treated in the initial stages of infection.","Strict and regular surveillance to notice the incidence of RPW in the field is utmost important for timely management","If infestation noticed, Neem oil emulsion @ 5ml/litre of water twotimes  in 15 days intervals can be sprayed."};
remedies_more.setVisibility(View.INVISIBLE);
        remedies_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result.getText().toString().equals("Leaf Spot")){
                 more=new Intent(CaptureImage.this,LeafSpot.class);
                startActivity(more);
                }
                else if(result.getText().toString().equals("Anabe Roga")){
                    more=new Intent(CaptureImage.this,AnabeRoga.class);
                    startActivity(more);
                }
                else if(result.getText().toString().equals("Bud Rot")){
                    more=new Intent(CaptureImage.this,MunduSiri.class);
                    startActivity(more);
                }
                else if(result.getText().toString().equals("Mite")){
                    more=new Intent(CaptureImage.this,Mite.class);
                    startActivity(more);
                }
               else  if(result.getText().toString().equals("Scale Insect")){
                    more=new Intent(CaptureImage.this,ScaleInsect.class);
                    startActivity(more);
                }
                else if(result.getText().toString().equals("Die Back")){
                    more=new Intent(CaptureImage.this,DieBack.class);
                    startActivity(more);
                }
               else if(result.getText().toString().equals("Red Palm Weevil")){
                    more=new Intent(CaptureImage.this,RedPalmWeevil.class);
                    startActivity(more);
                }
                else if(result.getText().toString().equals("Stem Bleeding")){
                    more=new Intent(CaptureImage.this,Stem_Bleeding.class);
                    startActivity(more);
                }


            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,3);
                }else{
                    requestPermissions(new String[]{Manifest.permission.CAMERA},100);
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent cameraIntent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    cameraIntent.setType("image/*");
                    String[] mimeTypes={"image/jpg","image/jpeg","image/png"};
                    cameraIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                    startActivityForResult(cameraIntent,1);


            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void classifyImage(Bitmap image){
    try {
        ModelUpdatedversion model = ModelUpdatedversion.newInstance(getApplicationContext());

        // Creates inputs for reference.
        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
        ByteBuffer byteBuffer=ByteBuffer.allocateDirect(4*imageSize*imageSize*3);
        byteBuffer.order(ByteOrder.nativeOrder());

        int[] intValues=new int[imageSize*imageSize];
        image.getPixels(intValues,0,image.getWidth(),0,0,image.getWidth(),image.getHeight());
        int pixel=0;

        for(int i=0;i<imageSize;i++){
            for(int j=0;j<imageSize;j++){
                int val=intValues[pixel++];
                byteBuffer.putFloat(((val >> 16) & 0xFF)*(1.f/255.f));
                byteBuffer.putFloat(((val >> 8) & 0xFF)*(1.f/255.f));
                byteBuffer.putFloat((val & 0xFF)*(1.f/255.f));
            }
        }
        inputFeature0.loadBuffer(byteBuffer);

        // Runs model inference and gets result.
        ModelUpdatedversion.Outputs outputs = model.process(inputFeature0);
        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

        float[] confidences=outputFeature0.getFloatArray();
        int maxPos=0;
        float maxConfidence=0;
example.setText(" ");
        for(int i=0;i<confidences.length;i++){
            example.setText(" ");
            if(confidences[i]>maxConfidence){

                maxConfidence=confidences[i];

                maxPos=i;
            }
        }
        String[] solutions={"Collection and burring of diseased leaves should be strictly followed to reduce the inoculum load.","Collection and burring of diseased leaves should be strictly followed to reduce the inoculum load.","Collection and burring of diseased leaves should be strictly followed to reduce the inoculum load."," "," ","Removal of resinous exudation/sawdust frass and injection of Chlorpyriphos 20EC@ 5ml/litre in bored holes. Cover the injected holes with wet soil to improve the efficiency of treatment.","Trenching of 30 cm width and 60 cm depth around the diseased\n palm and drenching with fungicides will help to arrest the further spread of the disease.","Field sanitation practices include removal and burning of infected  dried bunches to reduce the inoculum level in the garden should be strictly followed."," "," "," ","Application of neem oil emulsion (5 ml/litre water) two-times at 15 days intervals","Bud rot can be managed if the affected palms are treated in the initial stages of infection.","Strict and regular surveillance to notice the incidence of RPW in the field is utmost important for timely management","If infestation noticed, Neem oil emulsion @ 5ml/litre of water twotimes  in 15 days intervals can be sprayed."};

        String[] classes={"Leaf Spot", "Leaf Spot", "Leaf Spot", "Healthy", "Healthy", "Stem Bleeding", "Anabe Roga", "Die Back", "Healthy", "Healthy", "Healthy", "Mite", "Bud Rot", "Red Palm Weevil", "Scale Insect"};
        result.setText(classes[maxPos]);
        remedies.setText(solutions[maxPos]);
        if(!remedies.getText().toString().equals(" ")) {
            remedies_more.setVisibility(View.VISIBLE);
        }
        // Releases model resources if no longer used.
        model.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

}






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            Dialog dialog=new Dialog(this);
            CardView yesButton,noButton;
            if(requestCode==3){
                image=(Bitmap)data.getExtras().get("data");
                //imageView.setImageBitmap(image);
                int dimension=Math.min(image.getWidth(),image.getHeight());
                image= ThumbnailUtils.extractThumbnail(image,dimension,dimension);


                image=Bitmap.createScaledBitmap(image,imageSize,imageSize,false);

                dialog.setContentView(R.layout.upload_confirm_dialogue_box);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                yesButton=dialog.findViewById(R.id.yes_button);
                noButton=dialog.findViewById(R.id.no_button);
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView.setImageBitmap(image);
                        //detectImage(image);
                        classifyImage(image);
                        dialog.dismiss();
                    }
                });
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }else{

                Uri dat=data.getData();
                 image=null;
                 setimage=null;
                try{
                    setimage=MediaStore.Images.Media.getBitmap(this.getContentResolver(),dat);
                }catch(IOException e){
                    e.printStackTrace();
                }
                //imageView.setImageBitmap(image);

                image=Bitmap.createScaledBitmap(setimage,imageSize,imageSize,false);
                dialog.setContentView(R.layout.upload_confirm_dialogue_box);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                yesButton=dialog.findViewById(R.id.yes_button);
                noButton=dialog.findViewById(R.id.no_button);
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView.setImageBitmap(setimage);
                        //detectImage(image);
                        classifyImage(image);
                        dialog.dismiss();
                    }
                });
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}