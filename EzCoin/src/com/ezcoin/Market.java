package com.ezcoin;

import javax.swing.plaf.synth.SynthLookAndFeel;
import java.util.ArrayList;
import java.util.Scanner;

public class Market {
    ArrayList<Listing> listings;

    Market(){listings = new ArrayList<>();}
    public void showListings(){
        for (int i = 0; i < listings.size(); i++) {
            System.out.print(i + 1 + ": ");
            listings.get(i).printListing();
        }
    }

    public void buyItem(){
        if (listings.size() == 0){
            System.out.println("No items for sale.");
            return;
        }

        this.showListings();
        System.out.print("Select an Item to Buy: ");

        //Taking input.
        int in = 0;
        while (in < 1 || in > listings.size()) {
            Scanner sc = new Scanner(System.in);
            in = sc.nextInt();
        }
        buyListing(in - 1);
    }

    public void buyListing(int index){
        float tempPrice = listings.get(index).price; //Price of the item to be sold.
        User buyer = Main.currentUser;
        User seller = listings.get(index).seller;
        //Confirming if buyer has the money.
        if (buyer.wallet < tempPrice) {
            System.out.println("Insufficient Balance.");
            return;
        }
        Transaction newT = new Transaction(buyer.keys.getPrivateKey(), seller.keys.getPublicKey(),
                tempPrice, buyer, seller);
        System.out.println("Transaction Initiated. Status: Pending");
        listings.remove(index);
        Main.unconfirmedTransactions.add(newT); //Transaction to be added to the unverified list.
        //Set status of buyer and seller.
        Main.currentUser.status -= 1;
        seller.status -= 1;
    }

    //Put an item for selling on the market.
    public void sellItem(){
        System.out.print("Item Name: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();

        System.out.print("Item Price: ");
        float price = sc.nextInt();


        Listing newL = new Listing(Main.currentUser, price, name);
        listings.add(newL);
        System.out.println("\nListing made successfully :)");
    }

    public void showMarketMenu(){
        System.out.println("\nWelcome to the Market " + Main.currentUser.name);
        System.out.print("1. Buy an Item / See listings\n" +
                "2. Sell an Item\n" +
                "0. Go Back\n");

        //Taking input.
        System.out.print("Your Input: ");
        int in = -1;
        while (in < 0 || in > 2) {
            Scanner sc = new Scanner(System.in);
            in = sc.nextInt();
        }

        switch (in) {
            case 1:
                buyItem();
                break;
            case 2:
                sellItem();
                break;
            case 0:
                return;
        }
        showMarketMenu();
    }

}
