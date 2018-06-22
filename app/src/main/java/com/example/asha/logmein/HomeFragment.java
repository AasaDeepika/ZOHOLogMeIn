package com.example.asha.logmein;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.asha.logmein.GlobalClass.isHome;
import static com.example.asha.logmein.GlobalClass.Email;
import com.example.asha.logmein.R;

/**
 * Created by sridhar on 3/8/2018.
 */

public class HomeFragment extends Fragment  {
/*Alt + Insert to get a list of method*/

    private Toolbar toolbar;
    String Header, Content;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageButton Exercise;

    DatabaseHelper helper;
    Context context;
    Cursor c;
    String dName, dContact;
    TextView tv_name, tv_email, tv_contactNum;

    View myView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home_layout, container, false);
        isHome = true;

        //Getting context of the activity
        context = this.getActivity();

        //Creating a new databaseHelper object
        helper = new DatabaseHelper(context);

        //Fetching user name from table
        dName = helper.getUserName(Email);
        //Fetching Contact number from Table
        dContact = helper.getUserContact(Email);

        // Update the UI views with the latest data after changes
        tv_name = myView.findViewById(R.id.display_name);
        tv_email = myView.findViewById(R.id.display_email);
        tv_contactNum = myView.findViewById(R.id.display_contact_num);

        tv_name.setText(dName);
        tv_contactNum.setText(dContact);
        tv_email.setText(Email);

        return myView;


    }
}

