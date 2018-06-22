package com.example.asha.logmein;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import static com.example.asha.logmein.GlobalClass.package_name;
import static com.example.asha.logmein.GlobalClass.isHome;
import static com.example.asha.logmein.GlobalClass.Email;

public class WelcomeActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    boolean mPressedOnce = false;
    DatabaseHelper helper;
    Cursor c;
    String dName, dEmail,dContact;
    TextView tv_name, tv_email, tv_contactNum;

    private Toolbar toolbar;
    private NavigationView navigationView;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    String Header,Content;

    //To load fragments
    private static android.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            Email = bundle.getString("Email");
        }

        package_name = getPackageName();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting up navigation Drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Initialising navigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        isHome = true;
        toolbar.setTitle(R.string.app_name);
        fragment = new HomeFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Frags, fragment);
        fragmentTransaction.commit();
    }

    // Tab the device back button twice to exit
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
                if(isHome){
                    // If already in home, then allow double tap back to exit
                    if (mPressedOnce)
                    {
                        finish();
                        return;
                    }

                    this.mPressedOnce = true;
                    Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            mPressedOnce = false;
                        }
                    }, 2000);
                   /* //In Home Screen
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);*/
                }else{
                    //Go to home Screen
                    isHome = true;
                    toolbar.setTitle(R.string.app_name);
                    fragment = new HomeFragment();
                    fragmentManager = getFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.Frags, fragment);
                    fragmentTransaction.commit();
                    //super.onBackPressed();
                }
        }
    }//onBackPressed

    //On selecting the Navigation drawer item
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        //Displays the appropriate fragment, based on the list item selected
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        for(int i = 1; i < fragmentManager.getBackStackEntryCount(); i++){

            Log.d("backstack count","" + fragmentManager.getBackStackEntryCount());
            fragmentManager.popBackStack();
        }

        switch(id) {
            case R.id.nav_home:
                isHome = true;
                fragment = new HomeFragment();
                toolbar.setTitle(R.string.app_name);
                break;

            case R.id.nav_delete:
                isHome = false;
                fragment = new DeleteAccountFragment();
                toolbar.setTitle(R.string.delete_account_screen);
                break;

            case R.id.nav_edit:
                isHome = false;
                fragment = new EditProfileFragment();
                toolbar.setTitle(R.string.edit_profile_screen);
                break;
            case R.id.nav_signout:
                isHome = false;
                //finish activity
                finish();
                break;
            default:
                isHome = true;
                fragment = new HomeFragment();
                toolbar.setTitle(R.string.delete_account_screen);
                break;
        }

        fragmentTransaction.replace(R.id.Frags, fragment ,null).addToBackStack(null).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }//onNavigationItemSelected

    @Override
    public void onClick(View v) {

    }
}
