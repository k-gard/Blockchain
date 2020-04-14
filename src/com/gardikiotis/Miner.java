package com.gardikiotis;

import java.util.Date;

public class Miner extends Thread {
    private volatile BlockChain blockChain;

    private String data;
    public Miner(BlockChain b,String data) {
        this.blockChain = b;
        this.data = data;

     }

    public void mineBlock(String data) {
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
            generateBlock(data, start, id, previousBlockHash);
        }
    }

    private void generateBlock(String data, long start, int id, String previousBlockHash) {

        Block b = new Block(id, previousBlockHash, data);
        b.setBlockHex(blockChain.getHbs().HashBlock(b));
        b.setGenerationTime((float) (new Date().getTime() - start) / 1000);
        if (blockChain.isValidBlock(b)) {
            b.setCreatedBy(Thread.currentThread().getName().substring(14));
            blockChain.addBlock(b);
         //   System.out.println("Block data:" + b.getData());
        }
    }

    synchronized boolean getBlockChainState(){
        return blockChain.getBlockchain().isEmpty();
    }

    @Override
    public void run() {
  //      for (int i=0 ; i<=2 ; i++){
        mineBlock(this.data);//+" "+i);}
    }
}
