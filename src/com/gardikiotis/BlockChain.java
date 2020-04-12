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


    public HashBlockString getHbs() {
        return hbs;
    }

    public void setHbs(HashBlockString hbs) {
        this.hbs = hbs;
    }

    public Boolean isValid() {
        for (int i = 0; i < blockchain.size() - 1; i++) {
            if (!blockchain.get(i + 1).getPreviousBlockHexString().equals(blockchain.get(i).getBlockHex())) {
                return false;
            }
        }
        return true;
    }

    public  ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(ArrayList<Block> blockchain) {
        this.blockchain = blockchain;
    }
}

