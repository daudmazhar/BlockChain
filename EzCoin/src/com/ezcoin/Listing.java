package com.ezcoin;

public class Listing {
    User seller;
    float price;
    String name;

    Listing(User u, float p, String n){
        seller = u; price = p; name = n;
    }

    public void printListing(){
        System.out.println("Item: " + name + " --> " + price + " offered by: " + seller.name);
    }
}
