package com.gardikiotis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

class BlockChain implements Serializable {
    private ArrayList<Block> blockchain = new ArrayList<>();
    private ArrayList<String> blockchainDifficulty = new ArrayList<>();
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

    public Boolean isValidBlock(Block b) {
        StringBuilder prefix =new StringBuilder("");
        int i=hbs.getNumOfZeros();
        while (i > 0) {
            prefix.append("0");
            i--;
        }
        if (!b.getBlockHex().startsWith(prefix.toString())){return false;}
        if (b.getId() == 1) { return true;}
        return b.getPreviousBlockHexString().equals(blockchain.get(b.getId() - 2).getBlockHex());
    }

    public  ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(ArrayList<Block> blockchain) {
        this.blockchain = blockchain;
    }

    public void setDifficulty(Block b) {
        if (b.getGenerationTime() <= 10f){
            hbs.increaseDifficulty();
            blockchainDifficulty.add("Difficulty increased to "+hbs.getNumOfZeros());
        }
        if (b.getGenerationTime() >= 60f){
            hbs.decreaseDifficulty();
            blockchainDifficulty.add("Difficulty decreased to "+hbs.getNumOfZeros());
        }
    }

    public void addBlock(Block b){
        setDifficulty(b);
        blockchain.add(b);
    }

    public ArrayList<String> getBlockchainDifficulty() {
        return blockchainDifficulty;
    }
}

