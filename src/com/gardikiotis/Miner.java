package com.gardikiotis;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Miner extends Thread {
    private volatile BlockChain blockChain;

    private String data;
    public Miner(BlockChain b,String data) {
        this.blockChain = b;
        this.data = data;

    }
    public Miner(BlockChain b) {
        this.blockChain = b;
        //this.data = b.

    }

    public void mineBlock() throws Exception {
        long start = new Date().getTime();
        int id;
        String previousBlockHash;

        synchronized (Miner.class){
        if (blockChain.getBlockchain().isEmpty()) {
            id = 1;
            previousBlockHash = "0";
          //  data = "";
        } else {
            id = blockChain.getBlockchain().get(blockChain.getBlockchain().size() - 1).getId() + 1;
            previousBlockHash = blockChain.getBlockchain().get(blockChain.getBlockchain().size() - 1).getBlockHex();
         //   data = blockChain.getPendingMessage();

        }
            generateBlock(/*data, */start, id, previousBlockHash);
        }

    }

    private void generateBlock(/*String data,*/ long start, int id, String previousBlockHash) throws Exception {
        //System.out.println(Thread.currentThread().toString());
        Block b = new Block(id, previousBlockHash/*, data*/);
    //    BlockChain.acceptMessages(true);//

        if (id == 1L){
            b.setData("no Transactions");
            b.setBlockHex(blockChain.getHbs().HashBlock(b));
        //    System.out.println("100 vc awarded to miner" + Thread.currentThread().getName());

            b.setGenerationTime((float) (new Date().getTime() - start) / 1000);
        //    addBlockToBlockChain(b);

        } else {
          //  System.out.println("pending Transactions"+blockChain.getPendingTransactions().size());
            b.setTransactions(blockChain.getPendingTransactions());
            b.setData(b.getTransactions());
        }
        if (!b.getData().isEmpty() && !b.getData().equals("no Transactions")){

        b.setBlockHex(blockChain.getHbs().HashBlock(b));
        b.setGenerationTime((float) (new Date().getTime() - start) / 1000);

        ;}

        addBlockToBlockChain(b);


    }

    private synchronized void addBlockToBlockChain(Block b) throws Exception {
        if (blockChain.isValidBlock(b)) {

            b.setCreatedBy(Thread.currentThread().getName().substring(14));
            blockChain.addBlock(b);

            if (BlockChain.getInstance().getBalance(b.getCreatedBy()) > 0){

            sendVC();}
            MessageSender.generateMessages();

         //   System.out.println("Block data:" + b.getData());
        }
    }



    @Override
    public void run() {
        try {
            mineBlock();
            mineBlock();
            mineBlock();
         //   sendVCTo();
        } catch (Exception e) {
            e.printStackTrace();
        }
 /*       for (int i=0 ; i<=1 ; i++){
        try {
            System.out.println(Thread.currentThread().getName() + " #" + i);
            mineBlock();
            Thread.sleep(500L);
            //+" "+i);
        } catch (Exception e) {
            e.printStackTrace();
        }

          }*/
    }

    private void sendVC() throws Exception {
        ArrayList<Transaction> newTransactions = new ArrayList<>();
     //   System.out.println("miner #"+Thread.currentThread().getName() + "sending");
        int amount = 100;
        while (amount > 0){
            int i  = new Random().nextInt(50);
            amount = i > amount ? 0 : amount - i;
            newTransactions.add(MessageSender.generateMessage("miner # "+Thread.currentThread().getName().substring(14),i));
        }

        for (Transaction t : newTransactions ) {

            blockChain.addPendingTransaction(t);
        }
    }
}
