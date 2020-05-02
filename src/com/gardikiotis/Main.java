package com.gardikiotis;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static BlockChain blockchain;

    public static void main(String[] args) throws Exception {


        loadBlockChainPrompt();

        List<Thread> miners = new ArrayList<>();

        for (int i = 0; i < 5 ;i++){
        miners.add(new Miner(blockchain));}

        ExecutorService executor = Executors.newFixedThreadPool(5);
        miners.forEach(executor::submit);
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.MINUTES);

        printBlockChain(blockchain);

        saveBlockChain(blockchain);

    }



    private static void loadBlockChainPrompt() {
        Scanner s = new Scanner(System.in);
        System.out.println("Load existing BlockChain? (Y/N):");
        String str = s.nextLine();
        switch (str.toUpperCase()) {
            case "Y": loadBlockChain();
                      break;
            case "N": break;
            default:  loadBlockChainPrompt();
                      break;
        }
    }


    private static void saveBlockChain(BlockChain blockchain){

            try{
                System.out.println("Enter path to save BlockChain");
                Scanner scanner = new Scanner(System.in);
                Path path = Paths.get(scanner.nextLine());
                File file=new File(path.toString());
                if (!file.exists()) {
                    file.mkdir();
                    FileOutputStream f = new FileOutputStream(path.toString()+"\\blockchain.blc");
                    ObjectOutputStream o = new ObjectOutputStream(f);
                    o.writeObject(blockchain);
                    o.close();
                    f.close();

                }
                else {
                    System.out.println("Path already exists blockchain Loaded");
                }
            }catch (Exception e){
                System.out.println("Path error");
            }

    }




    private static void loadBlockChain(){
        try{
            System.out.println("Enter path to load BlockChain");
            Scanner scanner = new Scanner(System.in);
            Path path = Paths.get(scanner.nextLine());
            File file=new File(path.toString());
            if (!file.exists()) {
                System.out.println("File does not exist");
                blockchain = BlockChain.getInstance();
            }
            else {
                FileInputStream fi = new FileInputStream(new File(path.toString()+"\\blockchain.blc"));
                ObjectInputStream oi = new ObjectInputStream(fi);
                // Read objects
                blockchain=(BlockChain) oi.readObject();
                oi.close();
                fi.close();
            }
        }catch (Exception e){
            System.out.println("Path error");

        }



    }













    private static void printBlockChain(BlockChain blockchain) {
        for (int i = 0; i< blockchain.getBlockchain().size();i++ ) {
            System.out.println("Block:");
            System.out.println("Created by "+blockchain.getBlockchain().get(i).getCreatedBy());
            System.out.println(blockchain.getBlockchain().get(i).getCreatedBy() + " gets 100 VC");
            System.out.println("Id: " + blockchain.getBlockchain().get(i).getId());
            System.out.println("Timestamp: " + blockchain.getBlockchain().get(i).getTimestamp());
            System.out.println("Magic number: "+blockchain.getBlockchain().get(i).getMagicNumber());
            System.out.println("Hash of the previous block: ");
            System.out.println(blockchain.getBlockchain().get(i).getPreviousBlockHexString());
            System.out.println("Hash of the block:");
            System.out.println(blockchain.getBlockchain().get(i).getBlockHex());
            System.out.println("Block data:");
            System.out.println(blockchain.getBlockchain().get(i).getData());
            System.out.print("Block was generating for ");
            System.out.format("%.3f", blockchain.getBlockchain().get(i).getGenerationTime()) ;
            System.out.println(" seconds");
            System.out.println(blockchain.getBlockchainDifficulty().get(i));
            System.out.println();
        }
    }

}