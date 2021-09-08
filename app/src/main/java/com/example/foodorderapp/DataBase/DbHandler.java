package com.example.foodorderapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.foodorderapp.Models.OrdersModel;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class DbHandler extends SQLiteOpenHelper {
    final static String DBNAME = "myDataBase.db";
    final static int DBVERSION = 2;


    public DbHandler(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        id=0, name=1, phone=2, price=3, image=4, description=5, quantity=6, foodName=7;
         */
        db.execSQL("create table orders" +
                "(id integer primary key autoincrement ," +
                "name text," +
                "phone text," +
                "price int," +
                "image int," +
                "description text," +
                "quantity int," +
                "foodName text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists orders");
        onCreate(db);
    }

    public boolean insertOrder(String name, String phone, int price, int image, int quantity, String desc, String F_name) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("price", price);
        values.put("image", image);
        values.put("quantity", quantity);
        values.put("description", desc);
        values.put("foodName", F_name);
        long id = database.insert("orders", null, values);
        if (id <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<OrdersModel> getOrders() {
        ArrayList<OrdersModel> orders = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select id,foodName,image,price from orders", null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                OrdersModel ordersModel = new OrdersModel();
                ordersModel.setOrderNumber(cursor.getInt(0) + "");
                ordersModel.setSoldItemName(cursor.getString(1));
                ordersModel.setOrderImage(cursor.getInt(2));
                ordersModel.setPrice(cursor.getInt(3) + "");
                orders.add(ordersModel);
            }
        }
        cursor.close();
        database.close();
        return orders;
    }

    public Cursor getOrderById(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from orders where id=" + id, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    public boolean UpdateNow(String name, String phone, int price, int image, int quantity, String desc, String F_name,int id) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("price", price);
        values.put("image", image);
        values.put("quantity", quantity);
        values.put("description", desc);
        values.put("foodName", F_name);
        long row = database.update("orders",values,"id="+id,null);
        if (row <= 0) {
            return false;
        } else {
            return true;
        }
    }
    public int DeleteNow(String  id){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete("orders","id="+id,null);
    }

}
