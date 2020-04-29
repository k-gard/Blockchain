package com.gardikiotis;

import java.io.Serializable;

class HashBlockString implements Serializable {
    private int numOfZeros;

    public HashBlockString(int numOfZeros) {
        this.numOfZeros = numOfZeros;
    }

    public String HashBlock(Block b) {
        StringBuilder prefix = new StringBuilder("");
        int i = numOfZeros;
        while (i > 0) {
            prefix.append("0");
            i--;
        }
        String hash = StringUtil.applySha256(b.getBlockdata());
      //  System.out.println("Prefix:"+prefix+" hash:"+hash);
        while (!hash.startsWith(prefix.toString())) {
            b.initMagicNumber();
            b.updateMagicNumberBlockData();
            hash = StringUtil.applySha256(b.getBlockdata());

        }
        return hash;

    }

    public int getNumOfZeros() {
        return numOfZeros;
    }

    public void setNumOfZeros(int numOfZeros) {
        this.numOfZeros = numOfZeros;
    }

    public void increaseDifficulty(){
        this.numOfZeros++;
    }

    public void decreaseDifficulty(){
        this.numOfZeros--;
    }
}
