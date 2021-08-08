package com.ezcoin;

import com.ezcoin.Block;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class BlockChain {
    static ArrayList<Block> blockChain;

    BlockChain(){
        // Creating the GENESIS BLOCK
        blockChain = new ArrayList<Block>();
        blockChain.add(new Block(null));
    }

    public Block getLatestBlock(){
        return blockChain.get(blockChain.size() - 1);
    }

    public static String getLatestHash(){
        return blockChain.get(blockChain.size() - 1).hash;
    }

    public static void addBlock(Block b){
        if(!isValid()){
            System.out.println("BlockChain validity compromised!\n Cannot add more blocks to this chain.");
        }

        b.prevHash = getLatestHash();
        try {
            b.hash = b.calculateHash("SomeMiner");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        blockChain.add(b);
    }
    public static boolean isValid(){
        for(int i = blockChain.size() - 1; i > 0; i--){
            Block current = blockChain.get(i);
            Block prev = blockChain.get(i-1);
            String currentHash = "", prevHash = "";
            try{
                currentHash = current.calculateHash("SomeMiner");
                prevHash = prev.calculateHash("SomeMiner");
            }catch(NoSuchAlgorithmException e){
                e.printStackTrace();;
            }
            if (!current.hash.equals(currentHash)){
                return false;
            }
            if (!current.prevHash.equals(prevHash)) {
                return false;
            }
        }
        return true;
    }

    public void printChain(){
        for(int i = 0; i < blockChain.size(); i++){
            Block b = blockChain.get(i);
            System.out.println("hash: " + b.hash + "\n" + "previous hash: " + b.prevHash + "\n");
        }
    }
}
