package com.example.arecamithra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Remedies extends AppCompatActivity {
TextView textView;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedies);
        textView=findViewById(R.id.sy);
        reference = FirebaseDatabase.getInstance().getReference("diseases");
        Query checkUser=reference.orderByChild("disease").equalTo("AnebaRoga");

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    Toast.makeText(Remedies.this, "data", Toast.LENGTH_SHORT).show();
                    String text = snapshot.child("AnebaRoga").child("description").getValue(String.class);
int i;
                   String[] st=text.split("\n");

                    for (String line : st) {
                        Toast.makeText(Remedies.this, line, Toast.LENGTH_SHORT).show();

                        i=1;
                        textView.append(line);

                        i++;
                    }


                    textView.setText(text);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Remedies.this, "no data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}