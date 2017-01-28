package com.mobile_computing;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by Nicholas Cioli on 1/12/2017.
 */

public class Datum implements Serializable {
    private int    m_id;
    private String m_title;
    private String m_date;
    private String m_text;
    private String m_imageUrl;
    //Marking/unmarking a item as favourite
    private boolean favourite = false;

    //To Check if the item is favourite
    public boolean isFavourite(Context cont) {
        DB_fav db = new DB_fav(cont);
        SQLiteDatabase sqlnew = db.getWritableDatabase();
        Cursor c= sqlnew.query("favbutton", new String[]{"resultid"}, "resultid=" + this.id() , null, null, null, null);
        if(c.getCount() <= 0)
        {
            favourite=false;
        }
        else
        {
            favourite=true;
        }
        c.close();
        return favourite;

    }
//To set/unset an item as favourite in database according to user action
    public void setFavourite(boolean fav, Context cont) {
        DB_fav db = new DB_fav(cont);
        SQLiteDatabase sqlnew = db.getWritableDatabase();
        if(fav)//if it was not fav, add it to fav
        {
            ContentValues values = new ContentValues();
            values.put("resultid", this.id());
            sqlnew.insert("favbutton", null, values);
        }
        else//if it was fav, remove it from fav
        {
            sqlnew.delete("favbutton","resultid="+this.id(),null);
        }
        this.favourite = fav;
    }

    // Constructor
    Datum(int id, String title, String date, String text, String imageUrl) {
        m_id       = id;
        m_title    = title;
        m_date     = date;
        m_text     = text;
        m_imageUrl = imageUrl;
    }

    // Get operations
    public int id () { return m_id; }
    public String title() { return m_title; }
    public String date() { return m_date; }
    public String text() { return m_text; }
    public String imageUrl() { return m_imageUrl; }

}

//Database for local storage
class DB_fav extends SQLiteOpenHelper {
    final static String DB_NAME = "db_fav";
    public DB_fav(Context context) {
        super(context, DB_NAME, null, 1);
        // TODO Auto-generated constructor stub
    }
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS favbutton(resultid INTEGER PRIMARY KEY)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS db_fav");
        onCreate(db);

    }
}