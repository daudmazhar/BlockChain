package com.ezcoin;

public class Transaction {
    int senderPrivateKey;
    int receiverPublicKey;
    User sender, receiver;
    float amount;

    Transaction(int s, int r, float a){
        senderPrivateKey = s; receiverPublicKey = r; amount = a;
    }

    Transaction(int s, int r, float a, User sender, User receiver){
        senderPrivateKey = s; receiverPublicKey = r; amount = a; this.sender = sender; this.receiver = receiver;
    }

    public void printTransaction(){
        System.out.println("Transaction Information\nSender's Private Key: " + senderPrivateKey +
                "\nReceiver's Public Key: " + receiverPublicKey + "\nAmount Transferred: " + amount + " Ez Coins\n");
    }
}
