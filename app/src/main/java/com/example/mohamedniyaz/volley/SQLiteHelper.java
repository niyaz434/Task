package com.example.mohamedniyaz.volley;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String TABLE_NAME = "DETAIL";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_SKILLS = "SKILLS";
    public static final String COLUMN_IMAGE = "IMAGE";

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String database = "CREATE TABLE "+ TABLE_NAME + "("
                + COLUMN_ID + " INTEGER NOT NULL UNIQUE,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_SKILLS + " TEXT,"
                + COLUMN_IMAGE + " TEXT" + ")";

        db.execSQL(database);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);

        onCreate(db);

    }

    public void insert(int id, String name, String skills, String image){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put(COLUMN_ID, id);
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_SKILLS, skills);
            cv.put(COLUMN_IMAGE, image);

            sqLiteDatabase.insert(TABLE_NAME,null,cv);



        }catch (SQLException e){
            e.printStackTrace();

        }
    }

    public List<Data> getDetails()
    {
        List<Data> ar=new ArrayList<Data>();
        SQLiteDatabase sqldg=this.getReadableDatabase();
        Cursor cur=sqldg.rawQuery("select * from "+TABLE_NAME , null);
        if(cur!=null)
        {
            if(cur.moveToFirst())
            {
                do
                {
                    Data user=new Data();
                    user.setId(cur.getInt(0));
                    user.setName(cur.getString(1));
                    user.setSkill(cur.getString(2));
                    user.setImage(cur.getString(3));


                    ar.add(user);

                }
                while(cur.moveToNext());
            }
        }

        return ar;
    }

}
