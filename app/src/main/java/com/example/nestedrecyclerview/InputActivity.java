package com.example.nestedrecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InputActivity extends AppCompatActivity {
    ImageView imageView;
    EditText editText;
    Button button;
    Uri selectedImage;
    String imageUri;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        imageView= findViewById(R.id.imv1);
        editText= findViewById(R.id.edTv1);
        button= findViewById(R.id.add);
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,47);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentModel parentModel=new ParentModel();
                parentModel.setTvName(editText.getText().toString());
                String itemId=database.getReference().push().getKey();
                if(selectedImage!=null){
                    StorageReference reference=storage.getReference().child("Item").
                            child(itemId);
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageUri=uri.toString();
//                                        database.getReference().child("User").child(auth.getCurrentUser().getUid()).
//                                                child("profileImage").setValue(imageUri);
                                       // database.getReference().child("Item").child(itemId).
                                                //child("lists").child("list").setValue(imageUri);
                                        Toast.makeText(InputActivity.this, "Item Added", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Calendar calendar =Calendar.getInstance();
                                String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
                                String dateOfUpdation = "Last Update: "+currentDate+", ";

                                String updateBy = "Vikram";
//                                item.setUpdatedBy(updateBy);
//                                item.setUpdatedDate(dateOfUpdation);
                                ArrayList<ChildModel> childModels=new ArrayList<>();
                                //childModels.add(new ChildModel(imageUri));
                                parentModel.setList(childModels);
                                database.getReference().child("Item").child(itemId).setValue(parentModel);
                                //dialog.dismiss();
                                startActivity(new Intent(InputActivity.this,MainActivity.class));
                            }
                        }
                    });
                }else {
                    Toast.makeText(InputActivity.this, "Hiiiiiiii", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(data.getData()!=null){
                imageView.setImageURI(data.getData());
                selectedImage=data.getData();
            }
        }
    }
}