package com.ezcoin;

import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.util.ArrayList;

public class Miner extends Person{

    Block myBlock;
    Miner(float f, String n) {
        super(f, n);
    }

    public void createBlock(ArrayList<Transaction> pool){
        myBlock = new Block(pool);
    }

    public boolean mine() {
        int leadingZeros = 3;
        String check = "215";
        if (myBlock.hash.substring(0, leadingZeros).equals(check)) {
            System.out.println("SUCCESS!\nMiner " + this.name + " cracked the code.");
            System.out.println("Wallet Amount increased by 20\nNew Balance " + String.valueOf(this.wallet += (float) 20));
            for(int i = 0; i < myBlock.transactions.size(); i++){
                //Notifying the users that their transactions have gone through.
                //Updating their account balances
                float amount = myBlock.transactions.get(i).amount;
                myBlock.transactions.get(i).sender.status++;
                myBlock.transactions.get(i).receiver.status++;
                myBlock.transactions.get(i).receiver.changeBalance(amount);
                myBlock.transactions.get(i).sender.changeBalance(amount*-1);
            }
            BlockChain.addBlock(myBlock);
            return true;
        }
        else{
            myBlock.nonce++;
            try {
                myBlock.hash = myBlock.calculateHash(name);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public void printMinerDetails(){
        System.out.println("Name: " + name + '\n' + "Wallet: " + wallet + " Ez Coins");

    }
}
