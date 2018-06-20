package com.example.asha.logmein;

import android.database.Cursor;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {


    boolean mPressedOnce = false;
    DatabaseHelper helper;
    String Email;
    Cursor c;
    String dName, dEmail,dContact;
    TextView tv_name, tv_email, tv_contactNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            Email = bundle.getString("Email");
        }

        //Creating a new databaseHelper object
        helper = new DatabaseHelper(this);

        //Fetching user name from table
        dName = helper.getUserName(Email);
        //Fetching Contact number from Table
        dContact = helper.getUserContact(Email);

        // Update the UI views with the latest data after changes
        tv_name = findViewById(R.id.display_name);
        tv_email = findViewById(R.id.display_email);
        tv_contactNum = findViewById(R.id.display_contact_num);

        tv_name.setText(dName);
        tv_contactNum.setText(dContact);
        tv_email.setText(Email);

    }

    // Tab the device back button twice to exit
    @Override
    public void onBackPressed() {
        if (mPressedOnce) {
            super.onBackPressed();
        }
        this.mPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mPressedOnce =false;
            }
        }, 2000);
    }

}
