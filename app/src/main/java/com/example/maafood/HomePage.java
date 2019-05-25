package com.example.maafooD;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends AppCompatActivity implements RecyclerDishAdapter.OnItemClickListener, RecyclerOrderAdapter.OnItemClickListener {
    private static final String TAG = "MyActivity";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Button btnAddDish;
    Intent i;

    private FirebaseStorage mStorage;
    private FirebaseStorage oStorage;

    private RecyclerView mRecyclerView;
    private RecyclerView oRecyclerView;
    private RecyclerDishAdapter mAdapter;
    private RecyclerOrderAdapter oAdapter;
    private ProgressBar mProgressCircle;
    private ProgressBar oProgressCircle;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference oDatabaseRef;
    private List<dish> mUploads;
    private List<Order> oUploads;
    private ValueEventListener mDBListener;
    private ValueEventListener oDBListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setuptoolbar();

        //RecyclerView list = findViewById(R.id.recyclerDishList);
        //list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //  list.setAdapter(new RecyclerDishAdapter(new String[]{"dish1", "dish2", "dish 3", "dish 4", "dish 5", "dish 6", "dish 7", "dish 8"}));


        //RecyclerView list2 = findViewById(R.id.recyclerOrderList);
        //list2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // list2.setAdapter(new RecyclerDishAdapter(new String[]{"Order 1", "Order 2", "Order 3", "Order 4", "Order 5", "Order 6", "Order 7", "Order 8"}));

        mRecyclerView = findViewById(R.id.recyclerDishList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("dishimage");
        mDBListener =
                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            dish upload = postSnapshot.getValue(dish.class);
                            upload.setKey(postSnapshot.getKey());
                            mUploads.add(upload);
                        }
                        mAdapter = new RecyclerDishAdapter(HomePage.this, mUploads);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(HomePage.this);
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(HomePage.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });
/*
        oRecyclerView = findViewById(R.id.recyclerOrderList);
        oRecyclerView.setHasFixedSize(true);
        oRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        mProgressCircle = findViewById(R.id.progress_circle);
        oUploads = new ArrayList<>();
        oStorage = FirebaseStorage.getInstance();
        oDatabaseRef = FirebaseDatabase.getInstance().getReference("MyOrders/");
        oDBListener = oDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Order upload = postSnapshot.getValue(Order.class);
                    upload.setKey(postSnapshot.getKey());
                    oUploads.add(upload);
                }
                oAdapter = new RecyclerOrderAdapter(HomePage.this, oUploads);
                oRecyclerView.setAdapter(oAdapter);
                oAdapter.setOnItemClickListener(HomePage.this);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomePage.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
               mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });*/


        toolbar = findViewById(R.id.toolbar);
        btnAddDish = findViewById(R.id.btnAddDish);


        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        i = new Intent(HomePage.this, HomePage.class);
                        startActivity(i);
                        break;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        i = new Intent(HomePage.this, LoginActivity.class);
                        startActivity(i);
                        break;
                    case R.id.cravings:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        i = new Intent(HomePage.this, AddOrder.class);
                        startActivity(i);
                        break;


                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(this, "Whatever click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Toast.makeText(this, "delete click at position: " + position, Toast.LENGTH_SHORT).show();
        dish selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(HomePage.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

/*   public void order(View view){
        mRecyclerView = findViewById(R.id.recyclerOrderList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        mProgressCircle = findViewById(R.id.progress_circle);
        oUploads = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MyOrders/");
        mDBListener = oDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Order upload = postSnapshot.getValue(Order.class);
                    upload.setKey(postSnapshot.getKey());
                    oUploads.add(upload);
                }
                oAdapter = new RecyclerOrderAdapter(HomePage.this, oUploads);
                mRecyclerView.setAdapter(oAdapter);
                oAdapter.setOnItemClickListener(HomePage.this);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomePage.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }*/

    private void setuptoolbar() {

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Maa Food");
        toolbar.setSubtitle("House of healthy food");

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        /*  MenuItem item = menu.findItem(R.id.search_menu);
         SearchView searchView= (SearchView) item.getActionView();

       SearchView.OnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });*/

        return super.onCreateOptionsMenu(menu);
    }

    public void AddDish(View view) {

        Intent i = new Intent(HomePage.this, AddDish.class);
        startActivity(i);
    }

    public void AddOrder(View view) {
        Intent intent = new Intent(HomePage.this, AddOrder.class);
        startActivity(intent);
    }
}
