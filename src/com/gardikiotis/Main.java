package com.gardikiotis;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //   System.out.println("Hello World!");
        Scanner s = new Scanner(System.in);
        System.out.println("Enter how many zeros the hash must starts with:");
        BlockChain blockchain = new BlockChain(new HashBlockString(s.nextInt()));
        blockchain.createBlock("block 1 data");
        blockchain.createBlock("block 2 data");
        blockchain.createBlock("block 3 data");
        blockchain.createBlock("block 4 data");
        blockchain.createBlock("block 5 data");
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
          /*  System.out.println("Enter how many zeros the hash must starts with:");
            BlockChain blockchain2 = new BlockChain(new HashBlockString(s.nextInt()));
            blockchain2.createBlock("block 1 data");
            blockchain2.createBlock("block 2 data");
            blockchain2.createBlock("block 3 data");
            blockchain2.createBlock("block 4 data");
            blockchain2.createBlock("block 5 data");

        for (Block b : blockchain2.getBlockchain()) {
            System.out.println("Block:");
            System.out.println("Id :" + b.getId());
            System.out.println("Timestamp: " + b.getTimestamp());
            System.out.println("Magic number: "+b.getMagicNumber());
            System.out.println("Hash of the previous block: ");
            System.out.println(b.getPreviousBlockHexString());
            System.out.println("Hash of the block: ");
            System.out.println(b.getBlockHex());
            System.out.println("Block was generating for " + b.getGenerationTime() + " seconds");
            System.out.println();
        }

*/
        try {

            FileOutputStream f = new FileOutputStream(new File("C:\\Users\\x0r\\Desktop\\blockchain.blc"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(blockchain);
            //o.writeObject(blockchain2);

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
      /*  blockchain.createBlock("block 3 data");
        blockchain.createBlock("block 4 data");
        blockchain.createBlock("block 5 data");
        blockchain.createBlock("block 6 data");
        blockchain.createBlock("block 7 data");
        blockchain.createBlock("block 8 data");
        blockchain.createBlock("block 9 data");
        blockchain.createBlock("block 10 data");*/

    //Block b1=new Block(1,"0","block 1 data");
       /* Block b2=new Block(2,StringUtil.applySha256(b1.getBlockdata()),"block 2 data");
        Block b3=new Block(3,StringUtil.applySha256(b2.getBlockdata()),"block 3 data");
        Block b4=new Block(4,StringUtil.applySha256(b3.getBlockdata()),"block 4 data");
        Block b5=new Block(5,StringUtil.applySha256(b4.getBlockdata()),"block 5 data");
        Block b6=new Block(6,StringUtil.applySha256(b5.getBlockdata()),"block 6 data");
        Block b7=new Block(7,StringUtil.applySha256(b6.getBlockdata()),"block 7 data");
        Block b8=new Block(8,StringUtil.applySha256(b7.getBlockdata()),"block 8 data");
        Block b9=new Block(9,StringUtil.applySha256(b8.getBlockdata()),"block 9 data");
        Block b10=new Block(10,StringUtil.applySha256(b9.getBlockdata()),"block 10 data");*/
    // Blockchain.add(b1);
    //      System.out.println("Block:"+);
  /*      Blockchain.add(b2);
        Blockchain.add(b3);
        Blockchain.add(b4);
        Blockchain.add(b5);
        Blockchain.add(b6);
        Blockchain.add(b7);
        Blockchain.add(b8);
        Blockchain.add(b9);
        Blockchain.add(b10);



        for(int i=0;i<5 ;i++){
            System.out.println("Block:");
            System.out.println("Id:"+Blockchain.get(i).getId());
            System.out.println("Timestamp:"+Blockchain.get(i).getTimestamp());
            System.out.println("Hash of the previous block: ");
            System.out.println(Blockchain.get(i).getPreviousBlockHexString());
            System.out.println("Hash of the block:");
            System.out.println(StringUtil.applySha256(Blockchain.get(i).getBlockdata()));
            System.out.println();*/


}