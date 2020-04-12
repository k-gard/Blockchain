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
        while (!hash.startsWith(prefix.toString())) {
            b.initMagicNumber();
            b.updateMagicNumberBlockData();
            hash = StringUtil.applySha256(b.getBlockdata());

        }
        return hash;
    }
}