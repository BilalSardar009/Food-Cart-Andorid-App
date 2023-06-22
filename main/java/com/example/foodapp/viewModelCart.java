package com.example.foodapp;

import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.DB.DBHelper;
import com.example.foodapp.DB.IDB;
import com.example.foodapp.DB.IDBProduct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class viewModelCart extends ViewModel {
    ArrayList<CartItem> dataSet ;
    IDB DBCart;
    Context context;
    RecyclerView recycleview;
    MyCallback myCallback = null;
    int bal;

    public viewModelCart(IDB DB, Context c,MyCallback callback) {
        DBCart=DB;
        context=c;
        dataSet = new ArrayList<CartItem>();
        myCallback=callback;


    }

    public void setView( RecyclerView v)
    {
        recycleview=v;
    }
    public void FirebaseDataDisplay(){
        dataSet.clear();
        DBCart.getdataFirebase(new DBHelper.FireBaseCallBack(){
            @Override
            public void onCallback(ArrayList<CartItem> lists,int total) {
                for(CartItem list:lists){

                    CartItem c=new CartItem(list.getTitle(),list.getDescrption(),list.getPrice(),list.getImage(),list.getCount());
                    dataSet.add(c);
                }

                    myCallback.updateMyText(new String(String.valueOf(total)));


                CartAdapter myAdapter = new CartAdapter(context, dataSet, (DBHelper) DBCart);
                recycleview.setAdapter(myAdapter);
                recycleview.setLayoutManager(new LinearLayoutManager(context));

            }


        });


    }

    private void DBDataDisplay() {
        Cursor cursor=DBCart.getdata();
        if(cursor.getCount()==0)
        {
            Toast.makeText(context,"DB is Empty",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while (cursor.moveToNext())
            {
                CartItem c = new CartItem(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4));
                dataSet.add(c);
                Set<CartItem> s= new HashSet<CartItem>();
                s.addAll(dataSet);
                dataSet.clear();
                dataSet.addAll(s);
                bal = bal+100*cursor.getInt(4);

            }

        }

    }
/*
    private void getData(){
        boolean flag=false;
        if(getIntent().hasExtra("myImage")&& getIntent().hasExtra("data1")&& getIntent().hasExtra("data2")&& getIntent().hasExtra("data3")){
            if(dataSet.size()==0)
                flag=true;
            for(CartItem cartitem: dataSet)
            {
                if(cartitem.getTitle().equals(getIntent().getStringExtra("data1")))
                {
                    flag=false;
                    break;
                }
                flag=true;
            }
            if(flag==true) {
                CartItem c = new CartItem(getIntent().getStringExtra("data1"), getIntent().getStringExtra("data3"), getIntent().getStringExtra("data2"), getIntent().getIntExtra("myImage", 1), 0);
                dataSet.add(c);
                int bal = 100 * dataSet.size();
                bill.setText(new String(String.valueOf(bal)));
            }



        }
        else{
            Toast.makeText(context,"No data",Toast.LENGTH_SHORT).show();
        }
    }

 */
}
