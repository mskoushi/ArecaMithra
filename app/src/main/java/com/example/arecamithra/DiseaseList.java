package com.example.arecamithra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;

public class DiseaseList extends AppCompatActivity {
    ArrayList<DiseaseModel> arrDisease = new ArrayList<>();
ImageView back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_list);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.app_background, getTheme()));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        back_button=findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_disease);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        arrDisease.add(new DiseaseModel("Dakshina Kannada", "Dakshina Kannada", "Joint Director of Agriculture", "8277931060"));
        arrDisease.add(new DiseaseModel("Puttur", "Dakshina Kannada", "Deputy Director of Agriculture", "8277931062"));
        arrDisease.add(new DiseaseModel("Belthangadi", "Dakshina Kannada", "Assistant Director of Agriculture", "8277931066"));
        arrDisease.add(new DiseaseModel("Belthangadi", "Dakshina Kannada", "Assistant Director of Agriculture", "8277931067"));
        arrDisease.add(new DiseaseModel("Belthangadi", "Dakshina Kannada", "Assistant Director of Agriculture", "8277928881"));
        arrDisease.add(new DiseaseModel("Puttur", "Dakshina Kannada", "Assistant Director of Agriculture", "8277931068"));
        arrDisease.add(new DiseaseModel("Sullya", "Dakshina Kannada", "Assistant Director of Agriculture", "8277931069"));
        arrDisease.add(new DiseaseModel("Dakshina Kannada", "Dakshina Kannada", ",Agriculture Officer", "8277931071"));
        arrDisease.add(new DiseaseModel("Bantwal", "Dakshina Kannada", ",Agriculture Officer", "8277931072"));
        arrDisease.add(new DiseaseModel("Surathkal", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931074"));
        arrDisease.add(new DiseaseModel("Gurupura", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931075"));
        arrDisease.add(new DiseaseModel("Mudabidre", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931077"));
        arrDisease.add(new DiseaseModel("Bantwal", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931078"));
        arrDisease.add(new DiseaseModel("Bantwal", "Dakshina Kannada", "Agriculture Officer", "8277931079"));
        arrDisease.add(new DiseaseModel("Panemangaluru", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931080"));
        arrDisease.add(new DiseaseModel("Vittal", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931081"));
        arrDisease.add(new DiseaseModel("Kokkada", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931083"));
        arrDisease.add(new DiseaseModel("Belthangadi", "Dakshina Kannada", "Assistant Agriculture Officer", "8277928882"));
        arrDisease.add(new DiseaseModel("Belthangadi", "Dakshina Kannada", "Agriculture Officer", "8277931085"));
        arrDisease.add(new DiseaseModel("Puttur", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931086"));
        arrDisease.add(new DiseaseModel("Puttur", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931087"));
        arrDisease.add(new DiseaseModel("Puttur", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931088"));
        arrDisease.add(new DiseaseModel("Uppinagadi", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931089"));
        arrDisease.add(new DiseaseModel("Kadaba", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931090"));
        arrDisease.add(new DiseaseModel("Panja", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931092"));
        arrDisease.add(new DiseaseModel("Sullia", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931093"));
        arrDisease.add(new DiseaseModel("Panja", "Dakshina Kannada", "Assistant Agriculture Officer", "8277931094"));
        arrDisease.add(new DiseaseModel("Belthangady", "Dakshina Kannada", "Agriculture Officer", "8277928883"));
        arrDisease.add(new DiseaseModel("Mangaluru", "Dakshina Kannada", "Agriculture Officer", "8277928884"));
        arrDisease.add(new DiseaseModel("Puttur", "Dakshina Kannada", "Agriculture Officer", "8277928885"));
        arrDisease.add(new DiseaseModel("Surathkal", "Dakshina Kannada", "Agriculture Officer", "8277928886"));
        arrDisease.add(new DiseaseModel("Puttur", "Dakshina Kannada", "Agriculture Officer", "8277928887"));
        arrDisease.add(new DiseaseModel("Dakshina Kannada", "Dakshina Kannada", "Agriculture Officer", "8277928889"));

        RecyclerDiseaseAdapter adapter = new RecyclerDiseaseAdapter(this, arrDisease);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}