package com.ezcoin;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

public class Main {
    static ArrayList<Transaction> unconfirmedTransactions;
    Market market;
    ArrayList<User> users;
    ArrayList<Miner> miners;
    BlockChain chain;
    static User currentUser;
    File file;

    public static void main(String[] args) {
        Main main = new Main();
        main.controller();
    }

    public void controller() {
        initialize();
    }

    public void initialize() {
        unconfirmedTransactions = new ArrayList<>();
        market = new Market();
        chain = new BlockChain();
        miners = new ArrayList<>();
        users = new ArrayList<>();
        file = new File("blockchain.txt");
        writeToFile();
        showMenu();
    }

    public void showMenu() {
        //Making the first menu
        System.out.print("\nMAIN MENU\n1. Make new User\n" +
                "2. Make new Miner\n" +
                "3. Select current User\n" +
                "4. Go to Market\n" +
                "5. Start Mining round\n" +
                "6. View BlockChain\n" +
                "7. View UnVerified Transactions\n" +
                "8. View All People\n" +
                "0. Exit the Application" +
                "\nPlease Provide your input: ");

        //Taking input.
        int in = -1;
        while (in < 0 || in > 8) {
            Scanner sc = new Scanner(System.in);
            in = sc.nextInt();
        }

        switch (in) {
            case 1:
                addUser();
                break;
            case 2:
                addMiner();
                break;
            case 3:
                setUser();
                break;
            case 4:
                market.showMarketMenu();
                break;
            case 5:
                StartMining();
                break;
            case 6:
                chain.printChain();
                break;
            case 7:
                showTransactions();
                break;
            case 8:
                viewAllPeople();
                break;
            case 0:
                return;
        }
        System.out.println("\n");
        showMenu();
    }

    public void addUser() {
        System.out.print("Please enter the User's name: ");
        String in = new String();
        Scanner sc = new Scanner(System.in);
        in = sc.nextLine();

        System.out.print("Please input the User's balance: ");
        float temp;
        sc = new Scanner(System.in);
        temp = sc.nextFloat();

        users.add(new User(temp, in));

        System.out.println("User Name: " + in + " Balance: " + temp + " added with" +
                " Private Key = " + users.get(users.size() - 1).keys.getPrivateKey());

        if (users.size() == 1) {
            currentUser = users.get(0);
        }
    }

    public void addMiner() {
        System.out.print("Please enter the Miner's name: ");
        String in = new String();
        Scanner sc = new Scanner(System.in);
        in = sc.nextLine();

        System.out.print("Please input the Miner's balance: ");
        float temp;
        sc = new Scanner(System.in);
        temp = sc.nextFloat();

        miners.add(new Miner(temp, in));
        System.out.println(in + " " + temp + " added");
    }

    public void setUser() {
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            System.out.print(i + 1 +  ". Name: " + u.name + " Wallet: " + u.wallet);
            if(u.status == 0)
                System.out.println(" -> All Transactions Confirmed!");
            else
                System.out.println(" -> Pending Transactions: " + u.status*-1);
        }

        System.out.print("Select User Number: ");
        int input = 0;
        Scanner sc = new Scanner(System.in);
        input = sc.nextInt();

        currentUser = users.get(input - 1);
    }

    public void showTransactions(){
        for (int x = 0; x < unconfirmedTransactions.size(); x++)
            unconfirmedTransactions.get(x).printTransaction();
    }

    public void StartMining(){
        if (miners.size() == 0){
            System.out.println("No miners present kindly create some");
            return;
        }

        for(int i = 0; i < miners.size(); i++){
            miners.get(i).createBlock(unconfirmedTransactions);
        }
        boolean found = false;
        int turn = 0;
        while(!found) {
            found = miners.get(turn).mine();
            turn = (turn + 1) % miners.size();
        }

        //If block verified to be correct then write to all ledgers.
        if(verifyBlock())
            writeToFile();
    }

    public void writeToFile(){
        try {
            FileWriter fw = new FileWriter("blockchain.txt", true);
            fw.write("\n\nBlock " + BlockChain.blockChain.size());
            Block latestBlock = BlockChain.blockChain.get(BlockChain.blockChain.size() - 1);
            fw.write("\nhash: " + latestBlock.hash);
            fw.write("\nprev hash: " + latestBlock.prevHash);
            if(latestBlock.prevHash.equals("0")){
                fw.write("\nGenesis Block");
                fw.close();
                return;
            }
            fw.write("\nnonce: " + latestBlock.nonce);
            fw.write("\nTransactions: \n");

            for(int i = 0; i < latestBlock.transactions.size(); i++){
                Transaction tx =latestBlock.transactions.get(i);
                fw.write("Tx "+ i + ".\n");
                fw.write("sender: " + tx.senderPrivateKey);
                fw.write(("\nreceiver: " + tx.receiverPublicKey));
                fw.write("\namount: " + tx.amount + "\n\n");
            }

            fw.close();
        } catch (IOException e) {

            System.out.println("Exception");
            e.printStackTrace();
        }
    }

    public boolean verifyBlock(){
        ArrayList<Transaction> lastBlockTrans = chain.getLatestBlock().transactions;
        int totalMiners = miners.size();
        int minersThatVerify = totalMiners;
        for (int x = 0; x < totalMiners; x++){
            for (int y = 0; y < lastBlockTrans.size(); y++){
                if (lastBlockTrans.get(y).senderPrivateKey + lastBlockTrans.get(y).sender.keys.getPublicKey() != 0){
                    minersThatVerify--;
                    break;
                }
            }
        }

        if (minersThatVerify/totalMiners < 0.5){
            //Majority does not agree on the block.
            System.out.println("The last block could not be verified by miners. Removing it from the chain");
            BlockChain.blockChain.remove(BlockChain.blockChain.size() - 1);
            return false;
        }
        else {
            System.out.println("Block Verified by Majority Miners");
            return true;
        }
    }

    public void viewAllPeople(){
        System.out.println("Users: \n");
        for(int i = 0; i < users.size(); i++){
            User u = users.get(i);
            System.out.println("Name: " + u.name + "\nWallet: " + u.wallet + "\nPrivateKey: " + u.keys.getPrivateKey() + "\nPublicKey: " + u.keys.getPublicKey());
        }

        System.out.println("\nMiners:\n");
        for(int i = 0; i < miners.size(); i++){
            Miner m = miners.get(i);
            System.out.println("Name: " + m.name + "\nWallet: " + m.wallet);
        }

    }

}
