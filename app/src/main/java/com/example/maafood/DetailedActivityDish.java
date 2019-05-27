package com.example.maafooD;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.InvocationTargetException;

import static com.example.maafooD.HomePage.COST;
import static com.example.maafooD.HomePage.DESC;
import static com.example.maafooD.HomePage.DISH_NAME;
import static com.example.maafooD.HomePage.IMAGE_URL;
import static com.example.maafooD.HomePage.MAX;
import static com.example.maafooD.HomePage.MIN;

public class DetailedActivityDish extends AppCompatActivity {
    TextView name, desc, min, max, coast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_dish);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(IMAGE_URL);
        String dishName = intent.getStringExtra(DISH_NAME);
        String Desc = intent.getStringExtra(DESC);
        String min = intent.getStringExtra(MIN);
        String max = intent.getStringExtra(MAX);
        String cost = intent.getStringExtra(COST);

        ImageView imageView = findViewById(R.id.dish_image_detailed);
        TextView dishname = findViewById(R.id.DishName_detailed);
        TextView description = findViewById(R.id.description_detailed);
        TextView Min = findViewById(R.id.min_serving_detailed);
        TextView Max = findViewById(R.id.max_serving_detailed);
        TextView Coast = findViewById(R.id.EstimatedCoast_detailed);
        Button call = findViewById(R.id.Call);


        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        dishname.setText("Dish Name          =  " + dishName);
        description.setText("Description        =  " + Desc);
        Min.setText("Minimum Serving =  " + min);
        Max.setText("Maximum Serving =  " + max);
        Coast.setText("Estimated Cost  =  " + cost);


    }

    public  void CallUser(View view){

        Intent intent= new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0331 2276305"));
        startActivity(intent);
    }
    public  void message(View view){

    Intent intent= new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms","03312276305",null));
    intent.putExtra("sms body"," ");
        startActivity(intent);
    }
    public  void cancel(View view){

        Intent intent= new Intent(this,HomePage.class);

        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);

    }
}
