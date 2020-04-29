package com.gardikiotis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

class Block implements Serializable {
    private int id;
    private long timestamp;
    private String previousBlockHexString;
    private String blockHex;
    private long magicNumber;
    private String data;
    private String blockdata;
    private float generationTime;
    private List<Transaction> transactions = new ArrayList<>();


    private String createdBy;

    private int requiredZeros;

    public Block(Integer id, String previousBlockHexString, String data) {
        initMagicNumber();
        this.id = id;
        this.timestamp = new Date().getTime();
        this.previousBlockHexString = previousBlockHexString;
        this.data = data;
        this.blockdata = this.magicNumber + id.toString() + previousBlockHexString + data;
    }

    public Block(Integer id, String previousBlockHexString) {
        initMagicNumber();
        this.id = id;
        this.timestamp = new Date().getTime();
        this.previousBlockHexString = previousBlockHexString;
        this.data="";
        this.blockdata = this.magicNumber + id.toString() + previousBlockHexString + data;
    }



    public void initMagicNumber() {
        this.magicNumber = new Random().nextLong();
        if (this.magicNumber < 0) {
            initMagicNumber();
        }
    }

    public void updateMagicNumberBlockData() {
        this.blockdata = this.magicNumber + this.id + this.previousBlockHexString + this.data;
    }


    public int getRequiredZeros() {
        return requiredZeros;
    }

    public void setRequiredZeros(int requiredZeros) {
        this.requiredZeros = requiredZeros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getBlockHex() {
        return blockHex;
    }

    public void setBlockHex(String blockHex) {
        this.blockHex = blockHex;
    }

    public long getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(long magicNumber) {
        this.magicNumber = magicNumber;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPreviousBlockHexString() {
        return previousBlockHexString;
    }

    public void setPreviousBlockHexString(String previousBlockHexString) {
        this.previousBlockHexString = previousBlockHexString;
    }

    public String getBlockdata() {
        return blockdata;
    }

    public void setBlockdata(String blockdata) {
        this.blockdata = blockdata;
    }

    public String getData() {
        return data;
    }


    public void setData(List<Transaction> t) {

        StringBuilder s = new StringBuilder();
        if (t.size() == 1) {this.data = t.get(0).getTransactionLine();
        return ;}
        for (int i = 0 ; i < t.size() - 1 ; i++){
            s.append(t.get(i).getTransactionLine()).append("\n");
        }
        s.append(t.get(t.size() - 1).getTransactionLine());
        this.data = s.toString();
    }

    public void setData(String s) {
        this.data = s;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy ="miner # "+createdBy;
    }



    public float getGenerationTime() {
        return this.generationTime;
    }

    public void setGenerationTime(float generationTime) {
        this.generationTime = generationTime;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
