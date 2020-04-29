package com.gardikiotis;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        //   System.out.println("Hello World!");
        Scanner s = new Scanner(System.in);
       // System.out.println("Enter how many zeros the hash must starts with:");
       // File file = new File("C:\\Users\\x0r\\Desktop\\Keys");



        BlockChain blockchain = BlockChain.getInstance();
        Thread miner1=new Miner(blockchain);
        Thread miner2=new Miner(blockchain);
        Thread miner3=new Miner(blockchain);
        Thread miner4=new Miner(blockchain);
        Thread miner5=new Miner(blockchain);
        Thread miner6=new Miner(blockchain);
        Thread miner7=new Miner(blockchain);
        Thread miner8=new Miner(blockchain);
        Thread miner9=new Miner(blockchain);
        Thread miner10=new Miner(blockchain);
        /*Thread miner5=new Miner(blockchain);
        Thread miner5=new Miner(blockchain);
        Thread miner5=new Miner(blockchain);
        Thread miner5=new Miner(blockchain);*/
       // Thread miner5=new Miner(blockchain);

        Thread generator = new MessageSender();

        miner1.setName("1");
    //    miner1.start();
        miner2.setName("2");
     //   miner2.start();
        miner3.setName("3");
    //    miner3.start();
        miner4.setName("4");
   //     miner4.start();
        miner5.setName("5");
        miner6.setName("6");
        //    miner1.start();
        miner7.setName("7");
        //   miner2.start();
        miner8.setName("8");
        //    miner3.start();
        miner9.setName("9");
        //     miner4.start();
        miner10.setName("10");
   //     miner1.join();
  /*      miner1.setName("1");
        miner2.setName("2");
 /       miner2.join();
        miner3.setName("3");
        miner3.join();
        miner4.setName("4");
        miner4.join();*/

       /* Transaction m1 = new Transaction("John","Hi!");
        Transaction m2 = new Transaction("Mike","Hi John");
        Transaction m3 = new Transaction("John","How are you doing?");
        Transaction m4 = new Transaction("Mike","Doing well. How about you?");;
        Transaction m5 = new Transaction("John","I am great.Let's go for a coffee to catch up");
        Transaction m6 = new Transaction("Mike","Ok i will call you later.");*/

        LinkedList<String> linkedList = new LinkedList<>();
        /*linkedList.add(m1.getMessageLine());
        linkedList.add(m2.getMessageLine());
        linkedList.add(m3.getMessageLine());
        linkedList.add(m4.getMessageLine());
        linkedList.add(m5.getMessageLine());
        linkedList.add(m6.getMessageLine());
        //linkedList.add(m1.getMessageLine());
        blockchain.setBlockChainIncomingData(linkedList);*/
     //   generator.start();
     //   System.out.println(MessageSender.generateMessage().getMessageLine());

       //   for (int i=0 ; i<=2 ; i++) {
              ExecutorService executor = Executors.newFixedThreadPool(5);
              executor.submit(miner1);
              executor.submit(miner2);
              executor.submit(miner3);
              executor.submit(miner4);
              executor.submit(miner5);
     /*   executor.submit(miner6);
        executor.submit(miner7);
        executor.submit(miner8);
        executor.submit(miner9);
        executor.submit(miner10);*/

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.MINUTES);
       /* ExecutorService executor2 = Executors.newFixedThreadPool(5);
        executor2.submit(miner6);
        executor2.submit(miner7);
        executor2.submit(miner8);
        executor2.submit(miner9);
        executor2.submit(miner10);
        executor2.shutdown();
        executor2.awaitTermination(30, TimeUnit.MINUTES);*/
     //     }



      /*  miner.mineBlock("block 1 data");
        miner.mineBlock("block 2 data");
        miner.mineBlock("block 3 data");
        miner.mineBlock("block 4 data");
        miner.mineBlock("block 5 data");*/
        //   System.out.println(blockchain.isValid().toString());

//        for (Block b : blockchain.getBlockchain()) {
//            System.out.println("Block:");
//            System.out.println("Id: " + b.getId());
//            System.out.println("Timestamp: " + b.getTimestamp());
//            System.out.println("Magic number: "+b.getMagicNumber());
//            System.out.println("Hash of the previous block: ");
//            System.out.println(b.getPreviousBlockHexString());
//            System.out.println("Hash of the block:");
//            System.out.println(b.getBlockHex());
//            System.out.print("Block was generating for ");
//            System.out.format("%.3f", b.getGenerationTime()) ;
//            System.out.println(" seconds");
//            System.out.println();
//        }

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
        System.out.println("BlockChain Size:" + blockchain.getBlockchain().size());



        System.out.println("First Method:" +BlockChain.getInstance().getBalance("miner # 4"));
        System.out.println("Streams Method:" +BlockChain.getInstance().getBalance2("miner # 4"));
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


    }/**/

}