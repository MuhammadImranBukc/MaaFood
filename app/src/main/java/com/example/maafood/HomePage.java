package com.example.maafood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class HomePage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Button btnAddDish;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setuptoolbar();

        RecyclerView list =  findViewById(R.id.recyclerList);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list.setAdapter(new RecyclerViewAdapter(new String[]{"dish1", "dish2", "dish 3", "dish 4", "dish 5", "dish 6", "dish 7", "dish 8"}));


        toolbar =  findViewById(R.id.toolbar);
        btnAddDish =  findViewById(R.id.btnAddDish);


        navigationView =  findViewById(R.id.navigationView);
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


                }
                return false;
            }
        });
    }


    private void setuptoolbar() {

        drawerLayout =  findViewById(R.id.drawerLayout);
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

        Intent i = new Intent(this, AddDish.class);
        startActivity(i);
    }

}
