package com.example.foodapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.foodapp.CartItem;
import com.example.foodapp.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBHelperProduct extends SQLiteOpenHelper implements IDBProduct {
    ArrayList<Item> Items=new ArrayList<>();
    public DBHelperProduct(Context context) {
        super(context, "ProductData.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table ProductDetails(title TEXT primary key,description TEXT,price TEXT,image INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists ProductDetails");
    }

    public void insertProductDataFirebase(String title,String description,String price,int image){
        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("Product");

        Item t=new Item(title,description,price,image);
        reference.child(title).setValue(t);

    }
    @Override
    public  Boolean insertProductData(String title,String description,String price,int image){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("description",description);
        contentValues.put("price",price);
        contentValues.put("image",image);
        long result=DB.insert("ProductDetails",null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else{
            return  true;
        }
    }
    @Override
    public Cursor getdata(){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from ProductDetails",null);
        return cursor;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////    FireBase Data
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    public  void getdataFirebase(DBHelperProduct.FireBaseCallBack fireBaseCallBack){

        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("Product");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot:snapshot.getChildren()) {
                    Item c = datasnapshot.getValue(Item.class);
                    System.out.println(c.getTitle());
                    Items.add(c);
                }
                fireBaseCallBack.onCallback(Items);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.

            }
        });
    }
    public interface FireBaseCallBack{
        void onCallback(ArrayList<Item> lists);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////    FireBase Data
}
