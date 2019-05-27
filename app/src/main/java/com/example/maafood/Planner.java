package com.example.maafooD;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Planner extends AppCompatActivity {

    DatePickerDialog picker;
    public EditText dishname, people, date;
    public ProgressBar progressBar;
    DatabaseReference databaseReference;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        date = (EditText) findViewById(R.id.date);
        dishname = findViewById(R.id.dish_name);
        people = findViewById(R.id.people);
        date.setInputType(InputType.TYPE_NULL);


        databaseReference= FirebaseDatabase.getInstance().getReference("My Plans/");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        findViewById(R.id.btnAddOrder);
    }
    public void order(){

        String name = dishname.getText().toString();
        String peopl = people.getText().toString();
        String dat = date.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(peopl) && !TextUtils.isEmpty(dat)) {

            String id = databaseReference.push().getKey();
            Plan order= new Plan(id,name,peopl,dat);
            databaseReference.child(id).setValue(order);
            dishname.setText("");
            people.setText("");
            date.setText("");

            Toast.makeText(this,"Successfull",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
        else {

            Toast.makeText(this,"please fill this",Toast.LENGTH_LONG).show();

        }


    }
    public void Adorder(View view) {
        if (!TextUtils.isEmpty(dishname.getText()) && !TextUtils.isEmpty(people.getText()) && !TextUtils.isEmpty(date.getText())) {
            order();
        } else {

            Toast.makeText(this, "please fill this", Toast.LENGTH_LONG).show();

        }
    }


    public void selectDate(View view) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(Planner.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }

    public void back(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}