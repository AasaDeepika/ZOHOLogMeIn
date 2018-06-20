package com.example.asha.logmein;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERION =1;
    private static final String DB_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CONTACT_NUM = "contact";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID +
            " integer primary key not null , " + COLUMN_NAME + " text not null, " +
            COLUMN_EMAIL + " text not null,"+ COLUMN_PASSWORD + " text not null," +
            COLUMN_CONTACT_NUM + " text not null);";

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERION);
    }

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

    // Used to check if the given Email is present in the Table
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

    public Cursor getDisplayDetails(String email) {

        db = this.getReadableDatabase();
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{"name","email","contact"},null,null,null,null,null);
        return  cursor;

    }
}
