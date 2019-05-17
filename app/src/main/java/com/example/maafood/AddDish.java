package com.example.maafood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class AddDish extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int CHOOSE_IMAGE = 101;
    Spinner spinner;
    EditText dishName, description, minServing, maxServing, coast;
    ImageView imageView;
    Button button;
    Uri uriDishImage;
    ProgressBar progressBar;
    String dishImageUrl;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        spinner = findViewById(R.id.specifics);
        imageView = findViewById(R.id.dish_image);
        button = findViewById(R.id.button_post);
        progressBar = findViewById(R.id.progbar);
        dishName = findViewById(R.id.DishName);

        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.specifiecs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImageChooser();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });

    }

    private void saveUserInformation() {

        String dishNam = dishName.getText().toString();
        if (dishNam.isEmpty()) {
            dishName.setError("Dish name is required");
            dishName.requestFocus();
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest post = new UserProfileChangeRequest.Builder()
                    .setDisplayName(dishNam)
                    .setPhotoUri(Uri.parse(dishImageUrl))
                    .build();

            user.updateProfile(post)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddDish.this, "profile Updated", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null);

        uriDishImage = data.getData();
      //  imageView.setImageURI(uriDishImage);
        try {
           Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriDishImage);
            imageView.setImageBitmap(bitmap);
            uploadImageToFirebaseStorage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadImageToFirebaseStorage() {

        final StorageReference dishImageRef = FirebaseStorage.getInstance().getReference("dishimage/" + System.currentTimeMillis() + ".jpg");
        UploadTask uploadTask = dishImageRef.putFile(uriDishImage);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 taskSnapshot.getMetadata();
            }
        });




    }

    private void ShowImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Food Image"), CHOOSE_IMAGE);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
