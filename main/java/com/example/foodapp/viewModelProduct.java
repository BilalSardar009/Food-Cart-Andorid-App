package com.example.foodapp;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.DB.DBHelperProduct;
import com.example.foodapp.DB.IDBProduct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class viewModelProduct extends ViewModel {
        ArrayList<Item> dataSet ;
        IDBProduct DBproduct;
        Context context;
        RecyclerView recycleview;

        public viewModelProduct(IDBProduct DB, Context c) {
                DBproduct=DB;
                context=c;
                dataSet = new ArrayList<Item>();


        }

        public void setView( RecyclerView v)
        {
                recycleview=v;
        }
        public void FirebaseDataDisplay(){
               dataSet.clear();
                DBproduct.getdataFirebase(new DBHelperProduct.FireBaseCallBack(){
                        @Override
                        public void onCallback(ArrayList<Item> lists) {

                                for(Item list:lists){
                                        System.out.println(list.getTitle());
                                        Item c=new Item(list.getTitle(),list.getDescrption(),list.getPrice(),list.getImage());
                                       dataSet.add(c);
                                }
                                MyAdapter  myAdapter = new MyAdapter(context,dataSet);
                                recycleview.setAdapter(myAdapter);
                                recycleview.setLayoutManager(new LinearLayoutManager(context));
                        }


                });


        }
        private void DBDataDisplay() {
                Cursor cursor=DBproduct.getdata();
                if(cursor.getCount()==0)
                {
                        Toast.makeText(context,"DB is Empty",Toast.LENGTH_SHORT).show();
                        return;
                }
                else
                {
                        while (cursor.moveToNext())
                        {
                                Item c = new Item(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3));
                                dataSet.add(c);
                                Set<Item> s= new HashSet<Item>();
                                s.addAll(dataSet);
                                dataSet.clear();
                                dataSet.addAll(s);

                        }
                        Toast.makeText(context,"All Data Is Entered",Toast.LENGTH_SHORT).show();

                }

        }

}