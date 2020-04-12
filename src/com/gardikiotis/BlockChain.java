package com.gardikiotis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

class BlockChain implements Serializable {
    private ArrayList<Block> blockchain = new ArrayList<>();
    private HashBlockString hbs;

    public BlockChain(HashBlockString hbs) {
        this.hbs = hbs;
    }

    public void createBlock(String data) {
        long start=new Date().getTime();

        if (blockchain.isEmpty()) {
            Block b = new Block(1, "0", data);

            b.setBlockHex(hbs.HashBlock(b));
            b.setGenerationTime((float)(new Date().getTime()-start)/1000);
            blockchain.add(b);
            //    System.out.println("Block " + b.getId() + " created");
            //     System.out.println("Block " + b.getId() + " hash: " + b.getBlockHex());
        } else {
            Block b = new Block(blockchain.get(blockchain.size() - 1).getId() + 1, blockchain.get(blockchain.size() - 1).getBlockHex(), data);
            b.setBlockHex(hbs.HashBlock(b));
            b.setGenerationTime((float)(new Date().getTime()-start)/1000);

            blockchain.add(b);
            //  System.out.println("Block " + b.getId() + " created");
            //  System.out.println("Block " + b.getId() + " hash: " + b.getBlockHex());
        }


    }

    public Boolean isValid() {
        for (int i = 0; i < blockchain.size() - 1; i++) {
            if (!blockchain.get(i + 1).getPreviousBlockHexString().equals(blockchain.get(i).getBlockHex())) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(ArrayList<Block> blockchain) {
        this.blockchain = blockchain;
    }
}

