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
       // AsymmetricCryptography ac = new AsymmetricCryptography();
  //      PrivateKey privateKey = ac.getPrivate("C:\\Users\\x0r\\Desktop\\Keys\\privateKey");
   //     publicKey = ac.getPublic("C:\\Users\\x0r\\Desktop\\Keys\\publicKey");
    //    String[] strings = generateRandomWords(15);
        String sender = senders[new Random().nextInt(10)];
        String recipient = recipients[new Random().nextInt(10)];
        int amount = new Random().nextInt(10);
    //    String content = strings[1] + " " + strings[2] + " " + strings[3] + " " + strings[4] + " " + strings[5] + " " + strings[6];
        long id=BlockChain.getInstance().getTransactionId() + 1L;
        String text="{" + id/*(BlockChain.getInstance().getMessageId() + 1L)*/ +"}" + sender + recipient + amount;
        String signature =asymmetricCryptography.encryptText(text,privateKey);/*"{" + BlockChain.getInstance().getMessageId() + 1L +"}" + content,privateKey*///);
        return new Transaction(/*BlockChain.getInstance().getMessageId() + 1L*/id , sender ,recipient, signature, publicKey,amount);

    }

    public static Transaction generateMessage(String sender, int amount) throws Exception {
        if (!KeysInitializedFlag){
            InitializeKeys();}
      //  GenerateKeys.generateKeyPair("C:\\Users\\x0r\\Desktop\\Keys\\");
    //    AsymmetricCryptography ac = new AsymmetricCryptography();
    //    PrivateKey privateKey = ac.getPrivate("C:\\Users\\x0r\\Desktop\\Keys\\privateKey");
    //    publicKey = ac.getPublic("C:\\Users\\x0r\\Desktop\\Keys\\publicKey");
        String recipient = senders[new Random().nextInt(10)];

        //String content = strings[1] + " " + strings[2] + " " + strings[3] + " " + strings[4] + " " + strings[5] + " " + strings[6];
        long id=BlockChain.getInstance().getTransactionId() + 1L;
        String text="{" + id/*(BlockChain.getInstance().getMessageId() + 1L)*/ +"}" + sender + recipient + amount;
        String signature =asymmetricCryptography.encryptText(text,privateKey);/*"{" + BlockChain.getInstance().getMessageId() + 1L +"}" + content,privateKey*///);
        return new Transaction(/*BlockChain.getInstance().getMessageId() + 1L*/id , sender ,recipient, signature, publicKey,amount);

    }




    public static PublicKey getPublicKey() {
        return publicKey;
    }


    public static void generateMessages() throws Exception {
        if (!KeysInitializedFlag){
            InitializeKeys();}
       // GenerateKeys.generateKeyPair("C:\\Users\\x0r\\Desktop\\Keys\\");
        int i = 4;
        while (i>0) {

            BlockChain.getInstance().addPendingTransaction(generateMessage());
     //       sleep(3000);
           i--;
        }
    }

    @Override
    public void run() {
        try {
            generateMessages() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
/*

            int i = 12;
            while (i > 0) {

                BlockChain.getInstance().addPendingMessage("From : Transaction" + i);
                i--;
            }
*/


    }
    public static void InitializeKeys() throws NoSuchPaddingException, NoSuchAlgorithmException {
         asymmetricCryptography = new AsymmetricCryptography();
        try{
            System.out.println("Enter path for Transaction Client Emulator encryption keys");
            Scanner scanner = new Scanner(System.in);
            Path path = Paths.get(scanner.nextLine());
            File file=new File(path.toString());
            if (!file.exists()) {
                file.mkdir();
                file.setWritable(true);
                file.setReadable(true);
                //    System.out.println(path.toString());
                GenerateKeys.generateKeyPair(path.toString()/*"C:\\Users\\x0r\\Desktop\\Keys\\BlockChainKeys\\"*/);
                privateKey = asymmetricCryptography.getPrivate(path.toString()+"\\privateKey");//"C:\\Users\\x0r\\Desktop\\Keys\\BlockChainKeys\\privateKey");
                publicKey = asymmetricCryptography.getPublic(path.toString()+"\\publicKey");//"C:\\Users\\x0r\\Desktop\\Keys\\BlockChainKeys\\publicKey");
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

    public static void cancel(){

        isCanceled = true;
    }
}
