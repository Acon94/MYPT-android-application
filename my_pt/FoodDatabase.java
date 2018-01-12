package com.example.andrew.my_pt;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;


public class FoodDatabase extends AppCompatActivity implements View.OnClickListener {


    String key = "45d263e3555d4f0d528ea032cdb1f10e";// API key

    // Variables for activity
    private TextView result;
    private Button search;
    private Button add_item_btn;
    private EditText term;

    private EditText item_name;
    private EditText item_calories;
    private EditText item_protein;
    private EditText item_fat;
    private EditText item_carbohydrtes;
    private EditText item_amount;
    private String testString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_database);


        // find the componetns
        result = (TextView) findViewById(R.id.result);
        term = (EditText) findViewById(R.id.searchterm);
        search = (Button) findViewById(R.id.apisearch);
        add_item_btn = (Button) findViewById(R.id.add_item);


        item_name = (EditText) findViewById(R.id.item_name_field);
        item_calories = (EditText) findViewById(R.id.item_calories_field);
        item_protein = (EditText) findViewById(R.id.item_protein_field);
        item_fat = (EditText) findViewById(R.id.item_fat_field);
        item_carbohydrtes = (EditText) findViewById(R.id.item_cho_field);

        search.setOnClickListener(this);
        add_item_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == search)//+ term.getText()+
        {
            String rainurl =
                    String.format("https://api.nutritionix.com/v1_1/search/" + term.getText().toString().trim() + "?results=0:5&fields=item_name,brand_name,item_id,nf_calories,nf_protein,nf_total_fat,nf_total_carbohydrate&appId=8e6739c5&appKey=7aeff689b4a068ecc14f1dd4fb501358");

            new GetWeatherTask(result).execute(rainurl);//execute the Asynctask and pass the url
        }
        testString = item_fat.getText().toString() + item_calories.getText().toString();
        if (v == add_item_btn) {
            if (testString.equals("")) {
                Toast.makeText(this, "Searh or fill in data", Toast.LENGTH_LONG).show();
            } else {
                SQLiteDatabase db = openOrCreateDatabase("ListDB", Context.MODE_PRIVATE, null);
                db.execSQL("create table if not exists foodEatenNew(item_name VARCHAR,item_calories VARCHAR,item_protein VARCHAR,item_fat VARCHAR,item_cho VARCHAR);");



                ContentValues contentValuesFood = new ContentValues();// declare content values used when inserting the data

                String Mylistname = "Userinfo";

                // add required info to the content value
                contentValuesFood.put("item_name", item_name.getText().toString());
                contentValuesFood.put("item_calories", item_calories.getText().toString());
                contentValuesFood.put("item_protein", item_protein.getText().toString());
                contentValuesFood.put("item_fat", item_fat.getText().toString());
                contentValuesFood.put("item_cho", item_carbohydrtes.getText().toString());

                db.insert("foodEatenNew", null, contentValuesFood);//insert content value into the db table
            }
        }
    }


    private class GetWeatherTask extends AsyncTask<String, Void, String[]> {
        double rain = 0.0;
        String units = "imperial";
        private TextView t;

        public GetWeatherTask(TextView t) {
            this.t = t;
        }

        @Override
        protected String[] doInBackground(String... strings) {

            double rain = 0.0;
            String weather = "Sorry Nothing Found";
            String[] info = new String[10];
            try {

                URL rainurl = new URL(strings[0]);
                HttpURLConnection rainurlConnection = (HttpURLConnection) rainurl.openConnection();//open the connection to the ai call
                InputStream rainstream = new BufferedInputStream(rainurlConnection.getInputStream());
                BufferedReader rainbufferedReader = new BufferedReader(new InputStreamReader(rainstream));
                StringBuilder rainbuilder = new StringBuilder();

                String raininputString;
                while ((raininputString = rainbufferedReader.readLine()) != null) {
                    rainbuilder.append(raininputString);
                }
                JSONObject raintopLevel = new JSONObject(rainbuilder.toString());
                String listArray = raintopLevel.getString("hits");
                Log.d("Look at this here lad" + rain, "");
                String item_name = "";

                final int numberOfItemsInResp = raintopLevel.length();

                for (int x = 0; x < raintopLevel.length(); x++) {//loop through the results returned from the API
                    JSONArray hits = raintopLevel.getJSONArray("hits");
                    JSONObject obj = hits.getJSONObject(x); // first object in array

                    String id = obj.getString("_id");
                    JSONObject obj2 = obj.getJSONObject("fields");
                    String itemName = obj2.getString("item_name");
                    String fat = obj2.getString("nf_total_fat");
                    String cho = obj2.getString("nf_total_carbohydrate");
                    String protein = obj2.getString("nf_protein");
                    String cal = obj2.getString("nf_calories");

                    info[0] = itemName;
                    info[1] = fat;
                    info[2] = cho;
                    info[3] = protein;
                    info[4] = cal;

                    weather = " Item name" + itemName + " \nFAT" + fat + "\n Carbs" + cho + "\n Protein" + protein + "\n Calories" + cal;

                }
                rainurlConnection.disconnect();


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return info;
        }

        @Override
        protected void onPostExecute(String info[]) {


            item_name.setText(info[0]);
            item_calories.setText(info[4]);
            item_protein.setText(info[3]);
            item_carbohydrtes.setText(info[2]);
            item_fat.setText(info[1]);

            if (item_name.getText().toString().equals("")) {
                result.setText("");
                result.append("Sorry nothing Found try another term");
                result.setTextColor(Color.parseColor("#ff0000"));
            }
            if (!item_name.getText().toString().equals("")) {
                result.setText("");
                result.append("Success");
                result.setTextColor(Color.parseColor("#99cc00"));
            }

        }
    }

}



