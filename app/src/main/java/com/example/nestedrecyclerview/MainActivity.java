package com.example.nestedrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView prv;
    ArrayList<ParentModel> parentModels=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        prv=findViewById(R.id.parentRv);
        button=findViewById(R.id.input);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,InputActivity.class));
            }
        });
        for (int i=0;i<10;i++){
            ArrayList<ChildModel> childModels=new ArrayList<>();
            childModels.add(new ChildModel(R.drawable.ic_user));
            childModels.add(new ChildModel(R.drawable.ic_whatsapp));
            childModels.add(new ChildModel(R.drawable.ic_user));
            childModels.add(new ChildModel(R.drawable.ic_whatsapp));

            parentModels.add(new ParentModel("Rv "+i,childModels));
        }
        ParentAdapter adapter =new ParentAdapter(MainActivity.this,parentModels);
        prv.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
        prv.setLayoutManager(layoutManager);
        database.getReference().child("Item").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ParentModel model=dataSnapshot.getValue(ParentModel.class);
                    parentModels.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}