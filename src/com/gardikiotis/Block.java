package com.gardikiotis;

import java.io.Serializable;
import java.util.Date;
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

    public Block(Integer id, String previousBlockHexString, String data) {
        initMagicNumber();
        this.id = id;
        this.timestamp = new Date().getTime();
        this.previousBlockHexString = previousBlockHexString;
        this.data = data;
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

    public void setData(String data) {
        this.data = data;
    }

    public float getGenerationTime() {
        return this.generationTime;
    }

    public void setGenerationTime(float generationTime) {
        this.generationTime = generationTime;
    }
}
