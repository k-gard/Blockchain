package com.gardikiotis;


import java.io.Serializable;
import java.security.PublicKey;

public class Transaction implements Serializable {
    private String sender;
    private String recipient;
    private String content;
    private long id;
    private PublicKey publicKey;
    private String signature;
    private int vcAmount;



    public Transaction(long id, String sender, String recipient, String signature, PublicKey publicKey,int amount) {
        this.id = id ;
        this.sender = sender;
        this.recipient = recipient;
        this.content = sender + recipient + amount;
        this.publicKey = publicKey;
        this.signature = signature;
        this.vcAmount =amount;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTransactionLine(){
        return this.sender+" sent " + this.vcAmount + " VC to " + this.recipient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    public int getVcAmount() {
        return vcAmount;
    }

    public void setVcAmount(int vcAmount) {
        this.vcAmount = vcAmount;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
