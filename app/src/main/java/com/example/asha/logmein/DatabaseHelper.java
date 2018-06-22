package com.example.asha.logmein;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    //
    private static final int DB_VERION =1;

    //String variables that hold the database name and table name
    private static final String DB_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts";

    //String variables that hold the column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CONTACT_NUM = "contact";

    //SQLiteDatabase object
    SQLiteDatabase db;

    //Query to create table
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID +
            " integer primary key not null , " + COLUMN_NAME + " text not null, " +
            COLUMN_EMAIL + " text not null,"+ COLUMN_PASSWORD + " text not null," +
            COLUMN_CONTACT_NUM + " text not null);";

    //Constructor for the class
    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERION);
    }

    //Creates the users table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    //used to insert user data into the table
    public void insertContact(Contacts c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // To get the count of contact that are present in Table, which gives the
        // id of Row beig entered at the last
        String query = "Select * from contacts";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();


        values.put(COLUMN_ID,count);
        values.put(COLUMN_NAME,c.getName());
        values.put(COLUMN_EMAIL,c.getEmail());
        values.put(COLUMN_PASSWORD,c.getPassword());
        values.put(COLUMN_CONTACT_NUM,c.getContactNum());

        //Inserting the Contacts object into database
        db.insert(TABLE_NAME, null , values);
        cursor.close();
        db.close();
    }

    // Used to check if the given Email is present in the Table.
    public String searchEmail(String email)
    {
        db = this.getReadableDatabase();
        String query = "SELECT email from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String Email, EmailStatus;
        EmailStatus = "Not Found";
        if(cursor.moveToFirst())
        {
            do{
                Email  = cursor.getString(0);
                if(Email.equals(email))
                {
                    EmailStatus = "Found";
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return EmailStatus;
    }

    //Used to check if the password entered for the given Email is valid or not
    public String searchPass(String email)
    {
        db = this.getReadableDatabase();
        String query = "SELECT email, password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String Email, Password;
        Password = "Not Found";
        if(cursor.moveToFirst())
        {
            do{
                Email  = cursor.getString(0);
                if(Email.equals(email))
                {
                    Password = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return Password;
    }

    //Used to return the Contact number of the giver email
    public String getUserContact(String email)
    {
        db = this.getReadableDatabase();
        String query = "SELECT email, contact from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String Email, Contact = "";
        if(cursor.moveToFirst())
        {
            do{
                Email  = cursor.getString(0);
                if(Email.equals(email))
                {
                    Contact = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return Contact;
    }

    //Used to return the User name of the giver email
    public String getUserName(String email)
    {
        db = this.getReadableDatabase();
        String query = "SELECT email, name from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String Email, Name;
        Name = "";
        if(cursor.moveToFirst())
        {
            do{
                Email  = cursor.getString(0);
                if(Email.equals(email))
                {
                    Name = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return Name;
    }

    //Delete user with the given email from the table
    public void deleteUser(String email) {
        db = this.getWritableDatabase();
        //query to delete user with the given email
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE email = '"+ email + "'");

        db.close();
    }

    //Delete user with the given email from the table
    public void UpdateUserDetails(String sName, String sPassword, String sContact, String email) {
        db = this.getWritableDatabase();
        //query to delete user with the given email
        db.execSQL("UPDATE " + TABLE_NAME + " SET name = '"+ sName + "', password = '"+ sPassword +
        "', contact = '" + sContact + "' WHERE email = '" + email+"'");
        db.close();
    }
}
