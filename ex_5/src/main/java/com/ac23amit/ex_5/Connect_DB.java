package com.ac23amit.ex_5;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by win7 on 05/12/13.
 */
public class Connect_DB extends SQLiteOpenHelper
{
    private static Connect_DB ourInstance = null;
    private Context context;
    private ArrayList<ItemDetails> itemsArr = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDoLists_DB";// Database Name
    private static final String TABLE_TODOLIST = "ToDoLists_Tbl";// ToDoList table name
    // ToDoList Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ACT_ST = "status";
    private static final String KEY_PRICE = "price";

    private Connect_DB (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        itemsArr = new ArrayList<ItemDetails>();
    }

    @Override
    public void onCreate (SQLiteDatabase db)// Creating Tables
    {
        String CREATE_TODOLIST_TABLE = "CREATE TABLE " + TABLE_TODOLIST + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT ," + KEY_ACT_ST + " TEXT ," + KEY_PRICE + " TEXT" + ")";//
        try
        {
            db.execSQL(CREATE_TODOLIST_TABLE);
        }
        catch (SQLException e)
        {
            Toast.makeText(this.context, "cant do", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)// Upgrading database
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOLIST);// Drop older table if existed
        onCreate(db);// Create tables again
    }

    public int getSize ()
    {
        return itemsArr.size();
    }

    public void addItem (ItemDetails ItemP)
    {
        SQLiteDatabase db =null;
        try
        {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, ItemP.getName()); // ItemDetails Name//
            values.put(KEY_ACT_ST, ItemP.getbtnText()); // ItemDetails Phone Number
            values.put(KEY_PRICE, ItemP.getPrice()); // ItemDetails Phone Number
            db.insert(TABLE_TODOLIST, null, values);
            itemsArr.add(ItemP);

        }
        catch (Exception e)
        {
            Toast.makeText(this.context, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        finally
        {
            db.close(); // Closing database connection

        }
    }

    public long getElmID (int position)
    {
        return this.getElm(position).getId();
    }

    public void populateItemsArr ()
    {
        String selectQuery = "SELECT  * FROM " + TABLE_TODOLIST;//// Select All Query
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            cursor = db.rawQuery(selectQuery, null);
        }
        catch (Exception e)
        {
            Toast.makeText(this.context, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        if (cursor.moveToFirst())
        {//        looping through all rows and adding to list
            do
            {
                ItemDetails itemDetails = new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));//
                itemsArr.add(itemDetails);// Adding contact to list
            }
            while (cursor.moveToNext());
        }
        cursor.close();

    }

    public ArrayList<ItemDetails> getItems () { return itemsArr; }

    public ItemDetails getElm (int position) { return itemsArr.get(position); }

    public void deleteElm (int position )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Toast.makeText(this.context, " sis = " + id, 20).show();
        db.delete(TABLE_TODOLIST, KEY_ID + " = ?", new String[]{String.valueOf(this.getElmID(position))});
        itemsArr.remove(position);

        db.close();

    }

    public static synchronized Connect_DB getInstance (Context context)
    {
        if (ourInstance == null) ourInstance = new Connect_DB(context);
        return ourInstance;
    }

}

//    public ArrayList<ItemDetails> getItems ()
//    {
//        return itemsArr;
//    }
//Toast.makeText(this.context, "is", Toast.LENGTH_LONG).show();
//public void deleteInstruction ()
//{
//    if (itemsArr.get(0).getName().equals("enter todo activities")) itemsArr.remove(0);
//
//}
//public int getSize ()
//{
//    String countQuery = "SELECT  * FROM " + TABLE_TODOLIST;
//    SQLiteDatabase db = this.getReadableDatabase();
//    Cursor cursor = null;
//    int count = 0;
//    try
//    {
//        cursor = db.rawQuery(countQuery, null);
//        count = cursor.getCount();
//
//    }
//    catch (Exception e)
//    {
//        e.printStackTrace();
//        Toast.makeText(this.context, "Error ", 10).show();
//
//    }
//    finally
//    {
//        cursor.close();
//    }
//    return count;
////        return itemsArr.size();
//}
//List<ItemDetails> contactList = new ArrayList<ItemDetails>();

//        String selectQuery = "SELECT  * FROM " + TABLE_TODOLIST;//// Select All Query
//        Cursor cursor = null;
//        SQLiteDatabase db = this.getWritableDatabase();
//        try
//        {
//            cursor = db.rawQuery(selectQuery, null);
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(this.context, "cant do", Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//        finally
//        {
//            cursor.close();
//        }
//        Toast.makeText(this.context, " sis = ", 20).show();
//
////        looping through all rows and adding to list
//        if (cursor.moveToFirst())
//        {
//            do
//            {
//                ItemDetails itemDetails = new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));//
//                Toast.makeText(this.context, " sis = " + itemDetails, 20).show();
//                itemsArr.add(itemDetails);// Adding contact to list
//            }
//            while (cursor.moveToNext());
//        }
//        ItemDetails itemDetails = new ItemDetails(4, "ss", "ss", "ss");//
//        Toast.makeText(this.context, " sis = " + itemDetails, 20).show();
//        itemsArr.add(itemDetails);// Adding contact to list

//    public ItemDetails getElm (int id)
//    {
//        if (id == 0) id = 1;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        cursor = db.query(TABLE_TODOLIST, new String[]{KEY_ID , KEY_NAME , KEY_ACT_ST , KEY_PRICE}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null) cursor.moveToFirst();
//
//        ItemDetails cur = new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//        Toast.makeText(this.context, "is", Toast.LENGTH_LONG).show();
//        cursor.close();
//        return cur;
////        return itemsArr.get(id);
//    }
//public ItemDetails getElm (int id)
//{
//    if (id == 0) id = 1;
//    SQLiteDatabase db = this.getReadableDatabase();
//    Cursor cursor = null;
//    cursor = db.query(TABLE_TODOLIST, new String[]{KEY_ID , KEY_NAME , KEY_ACT_ST , KEY_PRICE}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
//    if (cursor != null) cursor.moveToFirst();
//
//    ItemDetails cur = new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//    Toast.makeText(this.context, "is", Toast.LENGTH_LONG).show();
//    cursor.close();
//    return cur;
////        return itemsArr.get(id);
//}
//Toast.makeText(this.context, " sis = " + itemDetails, 20).show();
