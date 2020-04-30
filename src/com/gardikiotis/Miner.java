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
    }

    public void mineBlock() throws Exception {
        long start = new Date().getTime();
        int id;
        String previousBlockHash;

        synchronized (Miner.class){
        if (blockChain.getBlockchain().isEmpty()) {
            id = 1;
            previousBlockHash = "0";
        } else {
            id = blockChain.getBlockchain().get(blockChain.getBlockchain().size() - 1).getId() + 1;
            previousBlockHash = blockChain.getBlockchain().get(blockChain.getBlockchain().size() - 1).getBlockHex();
        }
            generateBlock(start, id, previousBlockHash);
        }
    }

    private void generateBlock( long start, int id, String previousBlockHash) throws Exception {
        Block b = new Block(id, previousBlockHash);
        if (id == 1L){
            b.setData("no Transactions");
        } else {
            b.setTransactions(blockChain.getPendingTransactions());
            b.setData(b.getTransactions());
        }
        b.setBlockHex(blockChain.getHbs().HashBlock(b));
        b.setGenerationTime((float) (new Date().getTime() - start) / 1000);
        addBlockToBlockChain(b);
    }

    private synchronized void addBlockToBlockChain(Block b) throws Exception {
        if (blockChain.isValidBlock(b)) {
            b.setCreatedBy(Thread.currentThread().getName().substring(14));
            blockChain.addBlock(b);
       /*     if (BlockChain.getInstance().getBalance(b.getCreatedBy()) > 0){

            }*/
            MessageSender.generateMessages();
        }
    }



    @Override
    public void run() {
        try {
            mineBlock();
            sendVC();
            mineBlock();
            sendVC();
            mineBlock();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendVC() throws Exception {
        ArrayList<Transaction> newTransactions = new ArrayList<>();
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
