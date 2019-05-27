package com.example.maafooD;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class myProfile extends AppCompatActivity {

    private ImageView profilePic;
    private TextView profileName, profileNumber, profileEmail;
    private Button profileUpdate, changePassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        profilePic = findViewById(R.id.profileImage);
        profileName = findViewById(R.id.profileName);
        profileNumber = findViewById(R.id.profileNumber);
        profileEmail = findViewById(R.id.profileEmail);
        profileUpdate = findViewById(R.id.profileUpdate);

        mAuth = FirebaseAuth.getInstance();

        loadInfo();
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //firebaseAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance();
        //  firebaseStorage = FirebaseStorage.getInstance();

        //  DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        // StorageReference storageReference = firebaseStorage.getReference();
        // storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        //   @Override
        //       public void onSuccess(Uri uri) {
        //         Picasso.get().load(uri).fit().centerCrop().into(profilePic);
        //   }
        // });

       /*  databaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                 profileName.setText("Name  : " + userProfile.getUserName());
                 profileNumber.setText("Number  : " + userProfile.getUserNumber());
                 profileEmail.setText("Email  : " + userProfile.getUserEmail());
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         })  ;




       profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myProfile.this, UpdateProfile.class));
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myProfile.this, UpdatePassword.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void back(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);}

    public void loadInfo() {




    }

}

