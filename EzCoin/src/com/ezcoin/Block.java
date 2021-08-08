package com.ezcoin;

import java.sql.Time;
import java.util.ArrayList;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;

public class Block {
    String hash;
    String prevHash;
    ArrayList<Transaction> transactions;
    Timestamp timeStamp;
    int nonce;

    // Constructor for com.ezcoin.Block receives Transactions List and Previous com.ezcoin.Block Hash
    Block(ArrayList<Transaction> transactions) {
        this.timeStamp = Timestamp.from(Instant.now());
        this.transactions = transactions;
        this.nonce = 0;
        if (BlockChain.blockChain.size() == 0)
            this.prevHash = "0";
        else
            this.prevHash = BlockChain.getLatestHash();
        try {
            this.hash = calculateHash("SomeMiner");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    //Function to calculate Hash for the current com.ezcoin.Block
    public String calculateHash(String name) throws NoSuchAlgorithmException{
        String data = timeStamp.toString() + prevHash + String.valueOf(nonce) + name;
        if (!prevHash.equals("0"))
            data = data + this.transactions.toString();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(data.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}