package com.gardikiotis;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

public  class MessageSender extends Thread {
   private Transaction m;
   private static volatile boolean isCanceled = false;

   private static PublicKey publicKey;
   private static String[] senders= {"John","Bill","George","Mary","Helen","Peter","Daniel","Nick","Bob","Alice"};
   private static String[] recipients= {"CarShop","GamingShop","BeautyShop","FastFood","ShoesShop","Peter","Daniel","Nick","Bob","Alice"};
  // private static

   public static Transaction generateMessage() throws Exception {
        AsymmetricCryptography ac = new AsymmetricCryptography();
        PrivateKey privateKey = ac.getPrivate("C:\\Users\\x0r\\Desktop\\Keys\\privateKey");
        publicKey = ac.getPublic("C:\\Users\\x0r\\Desktop\\Keys\\publicKey");
    //    String[] strings = generateRandomWords(15);
        String sender = senders[new Random().nextInt(10)];
        String recipient = recipients[new Random().nextInt(10)];
        int amount = new Random().nextInt(10);
    //    String content = strings[1] + " " + strings[2] + " " + strings[3] + " " + strings[4] + " " + strings[5] + " " + strings[6];
        long id=BlockChain.getInstance().getMessageId() + 1L;
        String text="{" + id/*(BlockChain.getInstance().getMessageId() + 1L)*/ +"}" + sender + recipient + amount;
        String signature =ac.encryptText(text,privateKey);/*"{" + BlockChain.getInstance().getMessageId() + 1L +"}" + content,privateKey*///);
        return new Transaction(/*BlockChain.getInstance().getMessageId() + 1L*/id , sender ,recipient, signature, publicKey,amount);

    }

    public static Transaction generateMessage(String sender, int amount) throws Exception {
        GenerateKeys.generateKeyPair("C:\\Users\\x0r\\Desktop\\Keys\\");
        AsymmetricCryptography ac = new AsymmetricCryptography();
        PrivateKey privateKey = ac.getPrivate("C:\\Users\\x0r\\Desktop\\Keys\\privateKey");
        publicKey = ac.getPublic("C:\\Users\\x0r\\Desktop\\Keys\\publicKey");
        String recipient = senders[new Random().nextInt(10)];

        //String content = strings[1] + " " + strings[2] + " " + strings[3] + " " + strings[4] + " " + strings[5] + " " + strings[6];
        long id=BlockChain.getInstance().getMessageId() + 1L;
        String text="{" + id/*(BlockChain.getInstance().getMessageId() + 1L)*/ +"}" + sender + recipient + amount;
        String signature =ac.encryptText(text,privateKey);/*"{" + BlockChain.getInstance().getMessageId() + 1L +"}" + content,privateKey*///);
        return new Transaction(/*BlockChain.getInstance().getMessageId() + 1L*/id , sender ,recipient, signature, publicKey,amount);

    }




    public static PublicKey getPublicKey() {
        return publicKey;
    }

    public static String[] generateRandomWords(int numberOfWords)
    {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for(int i = 0; i < numberOfWords; i++)
        {
            char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
            for(int j = 0; j < word.length; j++)
            {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }

    public static void generateMessages() throws Exception {
        GenerateKeys.generateKeyPair("C:\\Users\\x0r\\Desktop\\Keys\\");
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

    public static void cancel(){

        isCanceled = true;
    }
}
