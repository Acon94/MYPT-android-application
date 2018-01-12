package com.example.andrew.my_pt;

/**
 * Created by Andrew on 11/03/2017.
 */

public class foodModel {
    //declare strings
    public String date;
    public String calories;
    public String protein;
    public String fat;
    public String cho;


    public foodModel() {
    }

    // constructor for food model
    public foodModel(String date, String calories, String protein, String fat, String cho) {
        this.date = date;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.cho = cho;
    }
}
