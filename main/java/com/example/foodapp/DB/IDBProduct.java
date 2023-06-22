package com.example.foodapp.DB;

import android.database.Cursor;

public interface IDBProduct {
    public  Boolean insertProductData(String title,String description,String price,int image);
    public Cursor getdata();
    public void insertProductDataFirebase(String title,String description,String price,int image);
    public  void getdataFirebase(DBHelperProduct.FireBaseCallBack fireBaseCallBack);
}
