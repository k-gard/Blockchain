package com.gardikiotis;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;
import java.util.Scanner;

public  class MessageSender extends Thread {
   private Transaction m;
   private static volatile boolean isCanceled = false;
   private static AsymmetricCryptography asymmetricCryptography;
   private static PrivateKey privateKey;
   private static PublicKey publicKey;
   private static String[] senders= {"John","Bill","George","Mary","Helen","Peter","Daniel","Nick","Bob","Alice"};
   private static String[] recipients= {"CarShop","GamingShop","BeautyShop","FastFood","ShoesShop","Peter","Daniel","Nick","Bob","Alice"};
   private static boolean KeysInitializedFlag = false;

   public static Transaction generateMessage() throws Exception {
       if (!KeysInitializedFlag){
           InitializeKeys();}
        String sender = senders[new Random().nextInt(10)];
        String recipient = recipients[new Random().nextInt(10)];
        int amount = new Random().nextInt(10);
        long id=BlockChain.getInstance().getTransactionId() + 1L;
        String text="{" + id +"}" + sender + recipient + amount;
        String signature =asymmetricCryptography.encryptText(text,privateKey);
        return new Transaction(id , sender ,recipient, signature, publicKey,amount);
    }

    public static Transaction generateMessage(String sender, int amount) throws Exception {
        if (!KeysInitializedFlag){
            InitializeKeys();}
        String recipient = senders[new Random().nextInt(10)];
        long id=BlockChain.getInstance().getTransactionId() + 1L;
        String text="{" + id +"}" + sender + recipient + amount;
        String signature =asymmetricCryptography.encryptText(text,privateKey);
        return new Transaction(id , sender ,recipient, signature, publicKey,amount);

    }

    public static PublicKey getPublicKey() {
        return publicKey;
    }

    public static void generateMessages() throws Exception {
        if (!KeysInitializedFlag){
            InitializeKeys();}
        int i = 4;
        while (i>0) {
            BlockChain.getInstance().addPendingTransaction(generateMessage());
           i--;
        }
    }

    @Override
    public void run() {
        try {
            InitializeKeys();
            int i=100;
            while (i > 0){
            generateMessages() ;
            i--;}
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static void InitializeKeys() throws NoSuchPaddingException, NoSuchAlgorithmException {
         asymmetricCryptography = new AsymmetricCryptography();
        try{
            System.out.println("Enter path for Transaction Client Emulator encryption keys");
            Scanner scanner = new Scanner(System.in);
            Path path = Paths.get(scanner.nextLine());
            File file=new File(path.toString());
            if (!file.exists()) {
                GenerateKeys.generateKeyPair(path.toString());
                privateKey = asymmetricCryptography.getPrivate(path.toString()+"\\privateKey");
                publicKey = asymmetricCryptography.getPublic(path.toString()+"\\publicKey");
                KeysInitializedFlag=true;
            }
            else {
                System.out.println("Path already exists");
                InitializeKeys();
            }
        }catch (Exception e){
            System.out.println("Path error");
            InitializeKeys();
        }
    }


}
