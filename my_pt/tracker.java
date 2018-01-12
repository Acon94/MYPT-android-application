package com.example.andrew.my_pt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class tracker extends AppCompatActivity implements View.OnClickListener {

    // Declare components and Variables
    Intent i;
    String dietBundle_string;

    Button completeBtn;
    Button clear_list;
    Button refresh_act;
    Button add_to_diet_btn;
    ListView lv;
    Double intake_count = 0.0;
    double calories;
    double value;
    double fat_val;
    double pro_val;
    double cho_val;
    double cal_count;
    double[] total_cal = new double[100];
    double[] total_pro = new double[100];
    double[] total_fat = new double[100];
    double[] total_cho = new double[100];

    double sum;
    double pro_sum, fat_sum, cho_sum = 0.0;
    int pro_int, fat_int, cho_int, b_int, food_sum = 0;
    double b;
    int a;

    ArrayAdapter adapter;
    ArrayList<String> food_list_array;
    String item_to_be_added;
    String item_to_be_added_calories;
    int item_to_be_added_id;
    String item_to_be_added_protein;
    String item_to_be_added_cho;
    String item_to_be_added_fat;


    SQLiteDatabase db;

    String food_intake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // GET the components by ID
        setContentView(R.layout.activity_tracker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        lv = (ListView) findViewById(R.id.diet_list);

        TextView goal = (TextView) findViewById(R.id.goal);
        TextView remaining = (TextView) findViewById(R.id.remaining);
        TextView food = (TextView) findViewById(R.id.fooddb);

        TextView protein = (TextView) findViewById(R.id.total_protein);
        TextView fat = (TextView) findViewById(R.id.total_fat);
        TextView cho = (TextView) findViewById(R.id.total_cho);


        setSupportActionBar(toolbar);

        completeBtn = (Button) findViewById(R.id.complete);
        clear_list = (Button) findViewById(R.id.clear_diet);
        refresh_act = (Button) findViewById(R.id.refresh_diet);
        add_to_diet_btn = (Button) findViewById(R.id.add_to_day);

        // set listeners on the buttons
        completeBtn.setOnClickListener(this);
        add_to_diet_btn.setOnClickListener(this);
        clear_list.setOnClickListener(this);
        refresh_act.setOnClickListener(this);

        // ACCess the database to read the

        db = openOrCreateDatabase("ListDB", Context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists foodEatenNew(item_id INTEGER PRIMARY KEY,item_name VARCHAR,item_calories VARCHAR,item_protein VARCHAR,item_fat VARCHAR,item_cho VARCHAR);");

        //get the data from the table
        Cursor c = db.rawQuery("select * from list where name = name ", null);
        String hit = "hit";
        Log.d("hit", hit);

       // move throughthe table and get the data
        if (c.moveToFirst()) {
            Log.d("IF HIT after click", c.getString(0));
            double bmr;
            int weight = Integer.parseInt(c.getString(1));
            int height = Integer.parseInt(c.getString(2));
            int age = Integer.parseInt(c.getString(3));
            Float activity = Float.parseFloat(c.getString(4));
            int acl = activity.intValue();

            // bmr calculation
            bmr = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
            calories = bmr * acl / 2;
            a = (int) Math.round(calories);
            b = a;

            String Scalories = "" + a;
            String Rcalories = "" + a;
            goal.setText(Scalories);
            remaining.setText(Rcalories);
        }
        //new courser to browse the food eaten table
        Cursor f =

                db.rawQuery("select * from foodEatenNew where item_name = item_name ", null);


        String entry;

        if (f.moveToFirst()) {

            food_list_array = new ArrayList<String>();

            while (f.isAfterLast() == false) {//use the cursore to pull thedat for each item in the table
                item_to_be_added = f.getString(f.getColumnIndex("item_name"));
                item_to_be_added_calories = f.getString(f.getColumnIndex("item_calories"));
                item_to_be_added_id = f.getInt(f.getColumnIndex("item_id"));
                item_to_be_added_protein = f.getString(f.getColumnIndex("item_protein"));
                item_to_be_added_fat = f.getString(f.getColumnIndex("item_fat"));
                item_to_be_added_cho = f.getString(f.getColumnIndex("item_cho"));


                entry = item_to_be_added_id + item_to_be_added + " Calories " + item_to_be_added_calories;
                food_list_array.add(entry);
                String intake = item_to_be_added_calories;

                food.setText(item_to_be_added_calories);
                protein.setText(item_to_be_added_protein);
                fat.setText(item_to_be_added_fat);
                cho.setText(item_to_be_added_cho);


                String text = food.getText().toString();
                String p = protein.getText().toString();
                String fa = fat.getText().toString();
                String ch = cho.getText().toString();

                if (!text.isEmpty())
                    try {

                        value = value + value;

                        for (int a = 0; a < 2; a++) {
                            value = Double.parseDouble(text);
                            cho_val = Double.parseDouble(ch);
                            pro_val = Double.parseDouble(p);
                            fat_val = Double.parseDouble(fa);


                            total_cal[a] = value;
                            total_cho[a] = cho_val;
                            total_pro[a] = pro_val;
                            total_fat[a] = fat_val;


                            sum += total_cal[a] / 2;

                            fat_sum += total_fat[a] / 2;
                            pro_sum += total_pro[a] / 2;
                            cho_sum += total_cho[a] / 2;

                            b = b - total_cal[a] / 2;
                            if (b < 0) {
                                remaining.setTextColor(Color.parseColor("#d43927"));
                            }
                        }


                        Math.round(b);

                        food_sum = (int) Math.round(sum);

                        b_int = (int) Math.round(b);

                        remaining.setText("" + b_int);

                        food.setText("" + food_sum + "");

                        Math.round(pro_sum);
                        Math.round(fat_sum);
                        Math.round(cho_sum);

                        pro_int = (int) Math.round(pro_sum);
                        fat_int = (int) Math.round(fat_sum);
                        cho_int = (int) Math.round(cho_sum);

                        protein.setText("Protein = " + pro_int + "g");
                        fat.setText("Fat = " + fat_int + "g");
                        cho.setText("Carbs = " + cho_int + "g");

                        i = new Intent(this, FoodBank.class);
                        dietBundle_string = " " + sum + "/" + pro_sum + "/" + cho_sum + "/" + fat_sum;

                        Bundle dietBundle = new Bundle();
                        dietBundle.putString("die", dietBundle_string);
                        i.putExtras(dietBundle);
                        // it means it is double
                    } catch (Exception e1) {
                        // this means it is not double
                        e1.printStackTrace();
                    }


                f.moveToNext();

                adapter = new ArrayAdapter<String>(this,
                        R.layout.listview_layout, food_list_array);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(tracker.this);
                        adb.setTitle("Remove iem ");
                        adb.setMessage("Are you sure you want to delete " + food_list_array.get(position));
                        final int positionToRemove = position;
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                food_list_array.remove(position);
                                Cursor del = db.rawQuery("delete from foodEatenNew where item_id =" + item_to_be_added_id, null);
                                del.moveToNext();
                                adapter.notifyDataSetChanged();

                            }
                        });
                        adb.show();

                    }

                });

            }


        }
    }

    @Override
    public void onClick(View v) {
        if (v == add_to_diet_btn) {

            startActivity(new Intent(this, FoodDatabase.class));
        } else if (v == completeBtn) {
            if (pro_sum == 0.0) {
                Toast.makeText(this, "Please add some food", Toast.LENGTH_LONG).show();
            } else if (pro_sum > 0.0) {
                startActivity(i);
            }
        } else if (v == refresh_act) {
            finish();
            startActivity(new Intent(this, tracker.class));
        } else if (v == clear_list) {
            if (pro_sum != 0.0 && fat_sum != 0.0) {

                Cursor g = db.rawQuery("delete from foodEatenNew", null);
                g.moveToNext();
                db.rawQuery("drop table foodEatenNew", null);


                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "List is empty", Toast.LENGTH_LONG).show();
            }
        }
    }
}
