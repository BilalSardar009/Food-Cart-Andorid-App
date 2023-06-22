package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.DB.DBHelper;

import java.util.ArrayList;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    private ArrayList<CartItem> mDataset;
    DBHelper Db;

    public CartAdapter(Context ct, ArrayList<CartItem> ds, DBHelper DB){
        context=ct;
       mDataset=ds;
       Db=DB;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.cart_rows,parent,false);

        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        holder.myText1.setText(mDataset.get(position).getTitle());
        holder.myText2.setText(mDataset.get(position).getDescrption());
        holder.myText3.setText(mDataset.get(position).getPrice());
        holder.myImage.setImageResource(mDataset.get(position).getImage());
        holder.myText4.setText(String.valueOf(mDataset.get(position).getCount()));
        holder.itemView.setTag(position);
        holder.plus.setTag(position);
        holder.minus.setTag(position);


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder {

        TextView myText1,myText2,myText3,myText4;
        ImageView myImage;
        ConstraintLayout mainLayout;
        public Button plus;
        public Button minus;
        public  CartViewHolder(@NonNull View itemView){
            super(itemView);
            myText1=itemView.findViewById(R.id.Cart_Title);
            myText2=itemView.findViewById(R.id.Cart_description);
            myText3=itemView.findViewById(R.id.Cart_price);
            myImage=itemView.findViewById(R.id.imageView3);
            mainLayout=itemView.findViewById(R.id.mainLayout);
            myText4=itemView.findViewById(R.id.Cart_count);
            plus = (Button) itemView.findViewById(R.id.plusButton);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                   int count=mDataset.get(pos).getCount();
                    Db.UpdateRow(mDataset.get(pos).getTitle(),mDataset.get(pos).getDescrption(),mDataset.get(pos).getPrice(),mDataset.get(pos).getImage(),++count);
                    Db.UpdateRowFireBase(mDataset.get(pos).getTitle(),mDataset.get(pos).getDescrption(),mDataset.get(pos).getPrice(),mDataset.get(pos).getImage(),count);
                    mDataset.get(pos).setCount(count);
                    notifyDataSetChanged();

                }
            });
            minus = (Button) itemView.findViewById(R.id.minusButton);
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if( mDataset.get(pos).getCount()>1)
                    {
                        int count=mDataset.get(pos).getCount();
                        Db.UpdateRow(mDataset.get(pos).getTitle(),mDataset.get(pos).getDescrption(),mDataset.get(pos).getPrice(),mDataset.get(pos).getImage(),--count);
                        Db.UpdateRowFireBase(mDataset.get(pos).getTitle(),mDataset.get(pos).getDescrption(),mDataset.get(pos).getPrice(),mDataset.get(pos).getImage(),count);
                        mDataset.get(pos).setCount(count);
                    }
                    else
                    {
                        Db.DeleteRow(mDataset.get(pos).getTitle());
                        Db.DeleteRowFirebase(mDataset.get(pos).getTitle());
                        mDataset.remove(pos);
                    }

                    notifyDataSetChanged();

                }
            });

        }
    }
}
