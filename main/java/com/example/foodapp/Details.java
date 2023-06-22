 package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.DB.DBHelper;
import com.example.foodapp.DB.IDB;

 public class Details extends AppCompatActivity {

    ImageView mainImageView;
    TextView title,description,price;
    String data1,data2,data3;
    int myImage;
    Button button;

     IDB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mainImageView=findViewById(R.id.imageView2);
        title=findViewById(R.id.textView);
        price=findViewById(R.id.textView2);
        description=findViewById(R.id.textView3);
        DB=new DBHelper(this);

        getData();
        setData();

        button=findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCart();
            }
        });


    }
    private void getData(){
        if(getIntent().hasExtra("myImage")&& getIntent().hasExtra("data1")&& getIntent().hasExtra("data2")&& getIntent().hasExtra("data3")){
            data1=getIntent().getStringExtra("data1");
            data2=getIntent().getStringExtra("data2");
            data3=getIntent().getStringExtra("data3");
            myImage=getIntent().getIntExtra("myImage",1);

        }
        else{
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }
    private void setData(){
        title.setText(data1);
        description.setText(data2);
        price.setText(data3);
        mainImageView.setImageResource(myImage);


    }
     public void onCart()
     {

         Boolean checkInsertData=DB.insertCartData(data1,data2,data3,myImage,1);
         DB.insertCartDataFirebase(data1,data2,data3,myImage,1);
         if(checkInsertData==true)
         {
             Toast.makeText(this,"Data Entered in the Db",Toast.LENGTH_SHORT).show();
         }
         else
         {
             Toast.makeText(this,"Data Does Not Entered in the Db",Toast.LENGTH_SHORT).show();
             Cursor c=DB.getParticularCount(data1);
             c.moveToFirst();
             int count=c.getInt(4);
             Toast.makeText(this,"updated value"+count,Toast.LENGTH_SHORT).show();
             DB.UpdateRow(data1, data2, data3, myImage, ++count);
             DB.UpdateRowFireBase(data1, data2, data3, myImage, count);

         }
         Intent intent=new Intent(this,cart.class);
         intent.putExtra("data1",data1);
         intent.putExtra("data2",data2);
         intent.putExtra("data3",data3);
         intent.putExtra("myImage",myImage);
         startActivity(intent);
     }
}