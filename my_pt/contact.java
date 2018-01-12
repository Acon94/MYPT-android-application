package com.example.andrew.my_pt;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class contact extends AppCompatActivity implements View.OnClickListener  {

    // declre components
    Button send_mail_btn;
    EditText recipient;
    EditText email_body_area;
    EditText subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        send_mail_btn = (Button) findViewById(R.id.send_email);
        recipient = (EditText) findViewById(R.id.email_recipient);
        subject = (EditText) findViewById(R.id.email_subject);
        email_body_area = (EditText) findViewById(R.id.email_body_content);

        send_mail_btn.setOnClickListener(this);


    }
    protected void sendEmail()
    {
        Log.d("Send email", "");
        String[] recipients = {recipient.getText().toString()};

        Intent MyemailIntent = new Intent(Intent.ACTION_SEND);
        MyemailIntent.setData(Uri.parse("mailto:"));
        MyemailIntent.setType("text/plain");

        // send a copy of the object using putExtra
        MyemailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        MyemailIntent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
        MyemailIntent.putExtra(Intent.EXTRA_TEXT, email_body_area.getText().toString());

        try {
            //trylauncha mail provider
            startActivity(Intent.createChooser(MyemailIntent, "Send mail..."));
            finish();
            Log.d("Finished  email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(contact.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {
        if (v == send_mail_btn) {
            sendEmail();
        }
    }
}
