package com.gardikiotis;

import java.util.Date;

public class Miner extends Thread {
    private volatile BlockChain blockChain;
    private static boolean isEmptyBlockchain;
    private String data;
    public Miner(BlockChain b,String data) {
        this.blockChain = b;
        this.data = data;
        this.isEmptyBlockchain = b.getBlockchain().isEmpty();
     }

    public void mineBlock(String data) {
        long start = new Date().getTime();
        int id;
        String previousBlockHash;
        synchronized (Miner.class){
        if (isEmptyBlockchain) {
            id = 1;
            previousBlockHash = "0";
            isEmptyBlockchain = false;
            Block b = new Block(id, previousBlockHash , data);
            b.setBlockHex(blockChain.getHbs().HashBlock(b));
            b.setGenerationTime((float) (new Date().getTime() - start) / 1000);
            blockChain.getBlockchain().add(b);
            System.out.println("Block data:" + b.getData() );
        } else {
            id = blockChain.getBlockchain().get(blockChain.getBlockchain().size() - 1).getId() + 1;
            previousBlockHash = blockChain.getBlockchain().get(blockChain.getBlockchain().size() - 1).getBlockHex();
            Block b = new Block(id, previousBlockHash , data);
            b.setBlockHex(blockChain.getHbs().HashBlock(b));
            b.setGenerationTime((float) (new Date().getTime() - start) / 1000);
            blockChain.getBlockchain().add(b);
            System.out.println("Block data:" + b.getData() );
        }}

        //    System.out.println("Block " + b.getId() + " created");
    }
    synchronized boolean getBlockChainState(){
        return blockChain.getBlockchain().isEmpty();
    }

    @Override
    public void run() {
        mineBlock(this.data);
    }
}
