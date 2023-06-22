package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.DB.DBHelper;
import com.example.foodapp.DB.IDB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class cart extends AppCompatActivity implements MyCallback{

    RecyclerView recycleview;
    ArrayList<CartItem> CartdataSet ;

    private viewModelCart viewmodel;
    TextView bill;
    int bal;
    Button button;
    IDB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        CartdataSet = new ArrayList<CartItem>();
        bill=findViewById(R.id.textView5);
        button=findViewById(R.id.button3);
        DB=new DBHelper(this);
        viewmodel = new viewModelCart(DB, this, new MyCallback() {
            @Override
            public void updateMyText(String myString) {
                ((TextView)findViewById(R.id.textView5)).setText(myString);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenu();
            }
        });

        recycleview=findViewById(R.id.recyclerView2);

        FirebaseDataDisplayViewModel();


        CartAdapter myAdapter= new CartAdapter(this,CartdataSet, (DBHelper) DB);
        recycleview.setAdapter(myAdapter);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        viewmodel.setView(recycleview);
    }

    private void FirebaseDataDisplayViewModel() {
        viewmodel.FirebaseDataDisplay();
    }



    public void onMenu()
    {
        Intent intent=new Intent(this,Recycleview.class);
        startActivity(intent);
    }

    @Override
    public void updateMyText(String myString) {

        ((TextView)findViewById(R.id.textView5)).setText(myString);
    }
}