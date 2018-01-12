package com.example.andrew.my_pt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;


public class userinfo extends AppCompatActivity implements View.OnClickListener {

    //compoments and variables

    Button save;
    Button retrieve;
    EditText listname;
    EditText display;
    EditText name;
    EditText height;
    EditText weight;
    EditText age;

    RatingBar actLevel;


    public static final String TABLE_NAME = "lists_table";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        int r, g, b;
        r = 255;
        g = 187;
        b = 51;

        float dt = 1;

        save = (Button) findViewById(R.id.save);
        retrieve = (Button) findViewById(R.id.retrive);
        actLevel = (RatingBar) findViewById(R.id.ratingBar);

        actLevel.setRating(dt);

        LayerDrawable stars = (LayerDrawable) actLevel.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(255, 187, 51), PorterDuff.Mode.SRC_ATOP);

        actLevel.setOnClickListener(this);
        retrieve.setOnClickListener(this);
        save.setOnClickListener(this);

        listname = (EditText) findViewById(R.id.cal);
        display = (EditText) findViewById(R.id.display);

        name = (EditText) findViewById(R.id.cal);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        age = (EditText) findViewById(R.id.age);


        SQLiteDatabase db = openOrCreateDatabase("ListDB", Context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists list(name VARCHAR,weight VARCHAR,height VARCHAR,age VARCHAR,level VARCHAR);");

        ContentValues contentValuesUser = new ContentValues();

        String Mylistname = "Userinfo";

        contentValuesUser.put("name", Mylistname);
        contentValuesUser.put("name", "Joe");
        contentValuesUser.put("weight", "145");
        contentValuesUser.put("height", "145");
        contentValuesUser.put("age", "21");
        contentValuesUser.put("level", "0");


        //Log.d("HERE LOOK", contentValues.toString());
        db.insert("list", null, contentValuesUser);

        Cursor c = db.rawQuery("select * from list where name = name ", null);
        String hit = "hit";
        Log.d("hit", hit);

        if (c.moveToFirst()) {
            Log.d("IF HIT after click", c.getString(0));

            name.setText(c.getString(0));
            height.setText(c.getString(2));
            weight.setText(c.getString(1));
            age.setText(c.getString(3));
            float x = Float.parseFloat(c.getString(4));
            actLevel.setRating(x);
        }


    }

    @Override
    public void onClick(View v) {
        String Mylistname = "Shopping";
        if (v == retrieve) {

            SQLiteDatabase db = openOrCreateDatabase("ListDB", Context.MODE_PRIVATE, null);
            Cursor c = db.rawQuery("select * from list where name = name ", null);

            if (c.moveToFirst()) {
                Log.d("IF HIT after click", c.getString(0));

                name.setText(c.getString(0));
                height.setText(c.getString(2));
                weight.setText(c.getString(1));
                age.setText(c.getString(3));
                float x = Float.parseFloat(c.getString(4));
                actLevel.setRating(x);


                //display.setText( c.getString(4));

            }
        } else if (v == save) {
            SQLiteDatabase db = openOrCreateDatabase("ListDB", Context.MODE_PRIVATE, null);


            db.execSQL("UPDATE list SET name='" + name.getText() + "',weight='" + weight.getText() + "', height='" + height.getText() + "',age='" + age.getText() + "',level='" + actLevel.getRating() + "' WHERE name = name");


        }


    }
}

