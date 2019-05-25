package com.example.maafooD;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddDish extends AppCompatActivity {


    public static final int PICK_IMAGE_REQUEST = 1;
    public Button AddDish;
    public ImageView DishImage;
    public EditText DishName, Desc, Max, Min, Coast;
    public ProgressBar ProgressBar;
    public static final String TAG = "SampleActivity";

    public Uri ImageUri;

    public StorageReference storageReference;
    public DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        AddDish = findViewById(R.id.btnAddDish);
        DishImage = findViewById(R.id.dish_image);
        DishName = findViewById(R.id.DishName);
        ProgressBar = findViewById(R.id.progbar);
        Desc = findViewById(R.id.description);
        Max = findViewById(R.id.max_serving);
        Min = findViewById(R.id.min_serving);
        Coast = findViewById(R.id.EstimatedCoast);

        storageReference = FirebaseStorage.getInstance().getReference("dishimage/");
        databaseReference = FirebaseDatabase.getInstance().getReference("dishimage/");

    }


    public void openFileChooser(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageUri = data.getData();
            Picasso.with(this).load(ImageUri).into(DishImage);
        }
    }


    public String getFileExtention(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadImage(View view) {
        if (ImageUri != null) {
            ProgressBar.setVisibility(View.VISIBLE);
             StorageReference filerefrence = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(ImageUri));
            filerefrence.putFile(ImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Toast.makeText(AddDish.this, "error agay", Toast.LENGTH_LONG).show();
                        throw task.getException();
                    }
                    return storageReference.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                Log.e(TAG, "then: " + downloadUri.toString());


                                dish dishClass = new dish(DishName.getText().toString().trim(),
                                        downloadUri.toString(), Min.getText().toString().trim(), Max.getText().toString().trim(), Coast.getText().toString().trim(), Desc.getText().toString().trim());
                                DishName.setText("");
                                Min.setText("");
                                Max.setText("");
                                Coast.setText("");
                                Desc.setText("");



                                databaseReference.push().setValue(dishClass);
                                ProgressBar.setVisibility(View.GONE);
                                Toast.makeText(AddDish.this, "Upload successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AddDish.this, HomePage.class);
                                startActivity(intent);
                            } else {
                                ProgressBar.setVisibility(View.GONE);
                                Toast.makeText(AddDish.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ProgressBar.setVisibility(View.GONE);
                            Toast.makeText(AddDish.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            ProgressBar.setVisibility(View.GONE);
            Toast.makeText(this, "No file selected", Toast.LENGTH_LONG).show();
        }

    }


    public void back(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);

    }


}
