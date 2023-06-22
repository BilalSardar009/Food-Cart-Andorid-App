package com.example.foodapp;

import java.util.Date;
import java.util.UUID;

public class Item {

    private String title;
    private String price;
    private String descrption
            ;
    private int image;

 public Item(){}
    public Item(String Title,String Price,String Descrption,int image){
        this.title = Title;
        this.price = Price;
        this.descrption
                = Descrption;
        this.image=image;
    }



    public String getTitle(){
        return title;
    }
    public String getPrice(){
        return price;
    }
    public int getImage(){ return image;}
    public String getDescrption(){
        return descrption
                ;
    }

}
