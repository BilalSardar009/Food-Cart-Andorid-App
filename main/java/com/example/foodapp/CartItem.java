package com.example.foodapp;

public class CartItem {
    private String title;
    private String price;
    private String descrption;
    private int image;
    private int count=1;


    public CartItem(){}
    public CartItem(String Title,String Price,String Descrption,int image,int cou){
        this.title = Title;
        this.price = Price;
        this.descrption = Descrption;
        this.image=image;
        this.count=cou;
    }



    public String getTitle(){
        return title;
    }
    public String getPrice(){
        return price;
    }
    public int getImage(){ return image;}
    public String getDescrption(){
        return descrption;
    }
    public int getCount(){
        return count;
    }

    public void setCount(int co){
        this.count=co;
    }

}


