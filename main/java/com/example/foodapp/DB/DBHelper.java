package com.example.foodapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.foodapp.CartItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper implements IDB {
    ArrayList<CartItem>  cartItems=new ArrayList<>();
    int total;
    public DBHelper(Context context) {
        super(context, "CartData.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
       ;
        DB.execSQL("create Table CartDetails(title TEXT primary key,description TEXT,price TEXT,image INTEGER,count INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists CartDetails");
    }


    public void insertCartDataFirebase(String title,String description,String price,int image,int count){
        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("Cart");

        CartItem c=new CartItem(title,description,price,image,count);
        reference.child(title).setValue(c);

    }
    public  Boolean insertCartData(String title,String description,String price,int image,int count){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("description",description);
        contentValues.put("price",price);
        contentValues.put("image",image);
        contentValues.put("count",count);
        long result=DB.insert("CartDetails",null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else{
            return  true;
        }
    }
    public Cursor getdata(){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from CartDetails",null);
        return cursor;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////    FireBase Data
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    public  void getdataFirebase(FireBaseCallBack fireBaseCallBack){

        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("Cart");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot:snapshot.getChildren()) {

                    CartItem c = datasnapshot.getValue(CartItem.class);
                    total+=c.getCount()*100;
                    System.out.println(c.getTitle());
                    cartItems.add(c);
                }
                fireBaseCallBack.onCallback(cartItems,total);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.

            }
        });
    }


    public interface FireBaseCallBack{
        void onCallback(ArrayList<CartItem> lists,int total);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////    FireBase Data
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Cursor getParticularCount(String title){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor  cursor = DB.rawQuery("Select * from CartDetails WHERE title=?",new String[]{title});
        return cursor;
    }
    public int DeleteRow(String title)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        return DB.delete("CartDetails","title=?",new String[]{title});
    }
    public void DeleteRowFirebase(String title)
    {
        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("Cart");
        reference.child(title).removeValue();
    }
    public void UpdateRowFireBase(String title,String description,String price,int image,int count)
    {
        FirebaseDatabase rootNode=FirebaseDatabase.getInstance("https://videocall-d1082-default-rtdb.firebaseio.com/");
        DatabaseReference reference=rootNode.getReference("Cart");

        CartItem c=new CartItem(title,description,price,image,count);
        reference.child(title).setValue(c);
    }
    public boolean UpdateRow(String title,String description,String price,int image,int count)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("description",description);
        contentValues.put("price",price);
        contentValues.put("image",image);
        contentValues.put("count",count);
        long result=DB.update("CartDetails", contentValues, "title=?",new String[]{title});
        if(result==-1)
        {
            return false;
        }
        else{
            return  true;
        }
    }

}
