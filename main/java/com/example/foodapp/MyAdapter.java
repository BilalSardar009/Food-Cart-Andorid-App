package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {
    private ArrayList<Item> mDataset;
    private ArrayList<Item> mDatasetfull;
    Context context;

    public MyAdapter(Context ct,ArrayList<Item> ds){

        context=ct;
        mDataset=ds;
        mDatasetfull=new ArrayList<>(ds);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.rows,parent,false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(mDataset.get(holder.getAdapterPosition()).getTitle());
        holder.myText2.setText(mDataset.get(holder.getAdapterPosition()).getDescrption());
        holder.myText3.setText(mDataset.get(holder.getAdapterPosition()).getPrice());
        holder.myImage.setImageResource(mDataset.get(holder.getAdapterPosition()).getImage());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Details.class);
                intent.putExtra("data1",mDataset.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("data2",mDataset.get(holder.getAdapterPosition()).getDescrption());
                intent.putExtra("data3",mDataset.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("myImage",mDataset.get(holder.getAdapterPosition()).getImage());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public Filter getFilter()
    {
        return filterList;
    }

    private Filter filterList=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Item> filteredList=new ArrayList<>();

            if (charSequence==null || charSequence.length()==0){
                filteredList.addAll(mDatasetfull);
            }
            else
            {
                String filterPattern=charSequence.toString().toLowerCase().trim();

                for(Item item: mDatasetfull){
                    if(item.getTitle().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }

            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            mDataset.clear();
            mDataset.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1,myText2,myText3;
        ImageView myImage;
        ConstraintLayout mainLayout;

        public  MyViewHolder(@NonNull View itemView){
            super(itemView);

            myText1=itemView.findViewById(R.id.Product_title);
            myText2=itemView.findViewById(R.id.Product_Desciption);
            myText3=itemView.findViewById(R.id.Product_prices);
            myImage=itemView.findViewById(R.id.imageView);
            mainLayout=itemView.findViewById(R.id.mainLayout);

        }
    }
}
