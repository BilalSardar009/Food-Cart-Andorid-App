package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.foodapp.DB.DBHelperProduct;
import com.example.foodapp.DB.IDBProduct;

public class Recycleview extends AppCompatActivity {
    RecyclerView recycleview;
    MyAdapter myAdapter;
    String s1[], s2[], s3[];
    IDBProduct DB;
    int images[] = {R.drawable.iphone, R.drawable.samsung, R.drawable.bread, R.drawable.shampoo, R.drawable.lappy, R.drawable.pixel, R.drawable.bag, R.drawable.mouse, R.drawable.ctype};
    //ArrayList<Item> dataSet = new ArrayList<Item>();
    private viewModelProduct viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);


        //viewmodel=new ViewModelProvider(this).get(viewModel.class);
        DB = new DBHelperProduct(this);
        recycleview = findViewById(R.id.recyclerView);
        viewmodel = new viewModelProduct(DB, this);

        EditText editText = findViewById(R.id.editText2);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        FirebaseDataDisplayViewModel();
        s1 = getResources().getStringArray(R.array.products);
        s2 = getResources().getStringArray(R.array.Desciption);
        s3 = getResources().getStringArray(R.array.Prices);

        for (int i = 0; i < s1.length; i++) {
            Boolean checkInsertData = DB.insertProductData(s1[i], s3[i], s2[2], images[i]);
            DB.insertProductDataFirebase(s1[i], s3[i], s2[2], images[i]);

        }

        myAdapter = new MyAdapter(this, viewmodel.dataSet);
        recycleview.setAdapter(myAdapter);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        viewmodel.setView(recycleview);
    }

    private void FirebaseDataDisplayViewModel() {
        viewmodel.FirebaseDataDisplay();
    }

    private void filter(String text) {

        myAdapter.getFilter().filter(text);
    }
}

