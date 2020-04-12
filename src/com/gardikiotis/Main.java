package com.gardikiotis;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //   System.out.println("Hello World!");
        Scanner s = new Scanner(System.in);
        System.out.println("Enter how many zeros the hash must starts with:");
        BlockChain blockchain = new BlockChain(new HashBlockString(s.nextInt()));
        Thread miner1=new Miner(blockchain,"block 1 data");
        Thread miner2=new Miner(blockchain,"block 2 data");
        Thread miner3=new Miner(blockchain,"block 3 data");
        Thread miner4=new Miner(blockchain,"block 4 data");

        miner1.start();
        miner2.start();
        miner3.start();
        miner4.start();
        miner1.join();
        miner2.join();
        miner3.join();
        miner4.join();

     /*   ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.submit(miner1);
        executor.submit(miner2);
        executor.submit(miner3);
        executor.submit(miner4);*/
       // executor.awaitTermination(1, TimeUnit.MINUTES );
       // executor.shutdown();



        /* miner.mineBlock("block 1 data");
        miner.mineBlock("block 2 data");
        miner.mineBlock("block 3 data");
        miner.mineBlock("block 4 data");
        miner.mineBlock("block 5 data");*/
        //   System.out.println(blockchain.isValid().toString());

        for (Block b : blockchain.getBlockchain()) {
            System.out.println("Block:");
            System.out.println("Id: " + b.getId());
            System.out.println("Timestamp: " + b.getTimestamp());
            System.out.println("Magic number: "+b.getMagicNumber());
            System.out.println("Hash of the previous block: ");
            System.out.println(b.getPreviousBlockHexString());
            System.out.println("Hash of the block:");
            System.out.println(b.getBlockHex());
            System.out.print("Block was generating for ");
            System.out.format("%.3f", b.getGenerationTime()) ;
            System.out.println(" seconds");
            System.out.println();
        }

        try {

            FileOutputStream f = new FileOutputStream(new File("C:\\Users\\x0r\\Desktop\\blockchain.blc"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(blockchain);


            o.close();
            f.close();

          /*  FileInputStream fi = new FileInputStream(new File("C:\\Users\\x0r\\Desktop\\blockchain.blc"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
           BlockChain blockChain=(BlockChain) oi.readObject();

            oi.close();
            fi.close();*/

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing stream");
        } /*catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/


    }

}