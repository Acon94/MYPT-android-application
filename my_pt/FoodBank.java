package com.example.andrew.my_pt;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FoodBank extends AppCompatActivity implements View.OnClickListener {


    //declare components and variables for activity
    private TextView fooddb;
    private TextView cal;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuthDB;
    private Button add;

    SQLiteDatabase db;

    private EditText pro,fat,cho;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_bank);

        db = openOrCreateDatabase("ListDB", Context.MODE_PRIVATE, null);//open connection to the DB

        databaseReference = FirebaseDatabase.getInstance().getReference();//get the usres firebase refrence
        firebaseAuthDB = FirebaseAuth.getInstance();

        //find the components by id
        fooddb = (EditText)findViewById(R.id.fooddb);
        cal = (EditText)findViewById(R.id.cal);
        add = (Button)findViewById(R.id.add);

        pro =(EditText)findViewById(R.id.final_pro);
        fat =(EditText)findViewById(R.id.final_fat);
        cho =(EditText)findViewById(R.id.final_cho);


        Bundle bundle = getIntent().getExtras();
        String stuff = bundle.getString("die");
        String test = stuff;


        //split the string passeed trough the bundle into an array
        String[] split_bundle_string = test.split("/");

        //get the date from the device
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("MMM MM dd, yyyy ");
        String dateString = sdf.format(date);
        fooddb.setText(dateString);

// add the athe index to the appropriate field
        String my_cal = split_bundle_string[0];
        cal.append(my_cal);
        fat.setText("fat " + split_bundle_string[3]);
        pro.setText("protein " + split_bundle_string[1]);
       cho.setText("cho " + split_bundle_string[2]);



        add.setOnClickListener(this);
    }

    public void savefoodModel()
    {

        //get the text from the fields
        String foodIN = fooddb.getText().toString().trim();
        String calIN = cal.getText().toString().trim();
        String proIN= pro.getText().toString().trim();
        String fatIN= fat.getText().toString().trim();
        String choIN= cho.getText().toString().trim();


        foodModel fm = new foodModel(foodIN,calIN,proIN,fatIN,choIN);//instance of the food model class
        FirebaseUser user = firebaseAuthDB.getCurrentUser();//get hte current logged in user
        databaseReference.child(user.getUid()).setValue(fm);//pass the refernece to the value uf the model
        Toast.makeText(this,"INFO SAVED",Toast.LENGTH_LONG).show();

    }


    @Override
    public void onClick(View v) {

        if(v == add)
        {

            savefoodModel();//call save modelmethod
            Cursor g = db.rawQuery("delete from foodEatenNew",null);//clesr the current days foos
            g.moveToNext();//cler nectitem
        }

    }
    //////////////////////\\\\\\\\\\\\\\\\\\\///////////////////\\\\\\\


    //////////////\\\\\\\\\\\\\\\\\////////////////////\\\\\\\\\\\\\\\\\
}
