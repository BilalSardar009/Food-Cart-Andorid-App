package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodapp.DB.DBHelperProduct;
import com.example.foodapp.DB.IDB;
import com.example.foodapp.DB.IDBProduct;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    private Button button,buttoncart,buttonSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        button=(Button) findViewById(R.id.button);
        buttoncart=(Button) findViewById(R.id.button4);
        buttonSupport=(Button) findViewById(R.id.buttonsupport);
        buttonSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:03204883958"));
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyleview();
            }
        });
        buttoncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oncart();
            }
        });
    }
    public void onRecyleview()
    {
        Intent intent=new Intent(this,Recycleview.class);
        startActivity(intent);
    }
    public void oncart()
    {
        Intent intent=new Intent(this,cart.class);
        startActivity(intent);
    }
}