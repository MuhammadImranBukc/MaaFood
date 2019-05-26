package com.example.maafooD;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.security.PublicKey;

@IgnoreExtraProperties
public class dish {
    private String DishName;
    private String ImageUrl;
    private String Min;
    private String Max;
    private String Coast;
    private String Desc;
    private String mKey;


    public dish() {
    }

    public String getMin() {
        return Min;
    }

    public String getMax() {
        return Max;
    }

    public void setMin(String min) {
        Min = min;
    }

    public void setMax(String max) {
        Max = max;
    }

    public void setCoast(String coast) {
        Coast = coast;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getCoast() {
        return Coast;
    }

    public String getDesc() {
        return Desc;
    }

    public dish(String dishName, String imageUrl, String min, String max, String coast, String desc) {
        DishName = dishName;
        ImageUrl = imageUrl;
        Min = min;
        Max = max;
        Coast = coast;
        Desc = desc;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }


    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
