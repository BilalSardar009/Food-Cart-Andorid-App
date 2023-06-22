package com.example.foodapp.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodapp.CartItem;

import java.util.ArrayList;

public interface IDB  {
    public void onUpgrade(SQLiteDatabase DB, int i, int ii);
    public  Boolean insertCartData(String title,String description,String price,int image,int count);
    public Cursor getdata();
    public int DeleteRow(String title);
    public boolean UpdateRow(String title,String description,String price,int image,int count);
    public Cursor getParticularCount(String title);
    public void insertCartDataFirebase(String title,String description,String price,int image,int count);
    public void UpdateRowFireBase(String title,String description,String price,int image,int count);
    public void DeleteRowFirebase(String title);
    public  void getdataFirebase(DBHelper.FireBaseCallBack fireBaseCallBack);

}
