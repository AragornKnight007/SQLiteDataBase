package com.example.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ContactsDB";
    private static final int  DATABASE_VERSION = 1;
    private  static final String TABLE_NAME = "contacts";

    private  static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_PHONE_NO = "PHONE_NO";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + "TEXT,"
                + KEY_PHONE_NO + " TEXT " + ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME );

        onCreate(sqLiteDatabase);

    }

    public void addContacts(String name, String phone_on){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_PHONE_NO,phone_on);

        db.insert(TABLE_NAME , null,values);




    }

    public ArrayList<ContactModel> fetchContacts(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);

        ArrayList<ContactModel> arrayList = new ArrayList<>();

        while (cursor.moveToNext()){

            ContactModel model = new ContactModel();
            model.ID = cursor.getInt(0);
            model.name= cursor.getString(1);
            model.phone_no= cursor.getString(2);

            arrayList.add(model);





        }

        return  arrayList;


    }


    public void updateContact(ContactModel contactModel){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_PHONE_NO,contactModel.phone_no);

        database.update(TABLE_NAME,cv,KEY_ID + " = " + contactModel.ID,null);


    }

    public void DeleteContact(int id){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME , KEY_ID + " = ? " , new String[]{String.valueOf(id)});




    }




}
