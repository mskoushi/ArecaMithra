package com.example.arecamithra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerDiseaseAdapter extends RecyclerView.Adapter<RecyclerDiseaseAdapter.ViewHolder> {
    Context context;
    ArrayList<DiseaseModel> arrDisease;
    RecyclerDiseaseAdapter(Context context, ArrayList<DiseaseModel> arrDisease){
        this.context=context;
        this.arrDisease=arrDisease;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.disease_card,parent,false);
       ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

holder.rphoneNumber.setText(arrDisease.get(position).phoneNumber);
holder.rdesignation.setText(arrDisease.get(position).designation);
holder.rplace.setText(arrDisease.get(position).place);
holder.rdistrict.setText(arrDisease.get(position).district);
        DiseaseModel item = arrDisease.get(position);
holder.call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String number=item.phoneNumber;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));

        context.startActivity(intent);

    }
});
    }

    @Override
    public int getItemCount() {
        return arrDisease.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rplace,rphoneNumber,rdesignation,rdistrict;
        CardView call;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rphoneNumber=itemView.findViewById(R.id.phoneNumber);
            rdesignation=itemView.findViewById(R.id.designation);
            rplace=itemView.findViewById(R.id.place);
            rdistrict=itemView.findViewById(R.id.district);
            call=itemView.findViewById(R.id.call);

        }
    }
}
