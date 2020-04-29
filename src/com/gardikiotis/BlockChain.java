package com.gardikiotis;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class BlockChain implements Serializable {
    private static BlockChain single_instance = null;
    private ArrayList<Block> blockchain = new ArrayList<>();
    private ArrayList<String> blockchainDifficulty = new ArrayList<>();
    private volatile LinkedList<Transaction> incomingTransactions = new LinkedList<>();
    private HashBlockString hbs;
    private long messageId = 120L;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    //private AsymmetricCryptography ac = new AsymmetricCryptography();;

    private BlockChain() throws Exception {
        AsymmetricCryptography ac = new AsymmetricCryptography();
        ;
        GenerateKeys.generateKeyPair("C:\\Users\\x0r\\Desktop\\Keys\\BlockChainKeys\\");

        this.privateKey = ac.getPrivate("C:\\Users\\x0r\\Desktop\\Keys\\BlockChainKeys\\privateKey");
        this.publicKey = ac.getPublic("C:\\Users\\x0r\\Desktop\\Keys\\BlockChainKeys\\publicKey");

        this.hbs = new HashBlockString(0);

    }

    public static BlockChain getInstance() throws Exception {
        if (single_instance == null)
            single_instance = new BlockChain();

        return single_instance;
    }

    public static void acceptMessages(boolean b) throws Exception {
        MessageSender.generateMessages();
    /*if (b){
        Thread messageSender =new MessageSender();
        messageSender.start();
        System.out.println("started");
    }
    else{
       System.out.println("canceled");
        MessageSender.cancel();}*/
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
        StringBuilder prefix = new StringBuilder("");
        int i = hbs.getNumOfZeros();
        while (i > 0) {
            prefix.append("0");
            i--;
        }
        if (!b.getBlockHex().startsWith(prefix.toString())) {
            return false;
        }
        if (b.getId() == 1) {
            return true;
        }
        return b.getPreviousBlockHexString().equals(blockchain.get(b.getId() - 2).getBlockHex());
    }

    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(ArrayList<Block> blockchain) {
        this.blockchain = blockchain;
    }

    public void setDifficulty(Block b) {
        if (b.getGenerationTime() <= 1f/*10f*/) {
            hbs.increaseDifficulty();
            blockchainDifficulty.add("Difficulty increased to " + hbs.getNumOfZeros());
            return;
        }
        if (b.getGenerationTime() >= 3f/*60f*/) {
            hbs.decreaseDifficulty();
            blockchainDifficulty.add("Difficulty decreased to " + hbs.getNumOfZeros());
            return;
        }
        blockchainDifficulty.add("Difficulty stays the same");
    }

    public void addBlock(Block b) throws Exception {
        setDifficulty(b);
        awardMiner(100, b);
        blockchain.add(b);
    }

    public ArrayList<String> getBlockchainDifficulty() {
        return blockchainDifficulty;
    }

    public void setBlockchainDifficulty(ArrayList<String> blockchainDifficulty) {
        this.blockchainDifficulty = blockchainDifficulty;
    }


    public LinkedList<Transaction> getIncomingTransactions() {
        return incomingTransactions;
    }

    public void setIncomingTransactions(LinkedList<Transaction> incomingTransactions) {
        this.incomingTransactions = incomingTransactions;
    }

    public void addPendingTransaction(Transaction incomingTransaction) {

        if (isValidTransaction(incomingTransaction)) {
            this.messageId++;
            this.incomingTransactions.push(incomingTransaction);
            //  System.out.println("_Added_");
            //        System.out.println(incomingTransaction.getMessageLine());
        }

    }

    public synchronized List<Transaction> getPendingTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        synchronized (Miner.class) {
           /* if (incomingTransactions.size() < 2) {
                return transactionList;
            }*/

            while (incomingTransactions.size() > 0) {
                transactionList.add(this.incomingTransactions.remove());
            }

            return transactionList;
        }
    }

    public long getMessageId() {
        return messageId;
    }


    public boolean isValidTransaction(Transaction m) {


        try {
            AsymmetricCryptography ac = new AsymmetricCryptography();
            String s = ac.decryptText(m.getSignature(), m.getPublicKey());
            if (m.getId() > messageId + 1) {
                //  System.out.println("Id issue");
                return false;
            }

            if (!m.getSender().equals("BlockChain") && m.getSender().contains("miner")) {
                if (getBalance(m.getSender()) < m.getVcAmount() &&
                        m.getVcAmount() > 0) { return false;}
           /*     int totalAmount = 0;
            for (Block b : BlockChain.getInstance().getBlockchain()) {
                    for (Transaction t : b.getTransactions()) {
                        System.out.println("TransactionID:"+t.getId());
                        if (t.getRecipient().equals(m.getSender())) {
                            totalAmount += t.getVcAmount();
                            System.out.println("RES"+t.getRecipient());
                            System.out.println("SEN"+m.getSender());
                            System.out.println("+ recipient=sender total/trans " + totalAmount+"/"+t.getVcAmount());
                        }
                        if (t.getSender().equals(m.getSender())) {
                            System.out.println("RES"+t.getRecipient());
                            System.out.println("SEN"+m.getSender());
                            System.out.println("- sender=sender total/trans " + totalAmount+"/"+t.getVcAmount());
                            totalAmount -= t.getVcAmount();
                        }
                        System.out.println("-------------------------------------------------------------------" );
                    }
                if (totalAmount < m.getVcAmount() || m.getVcAmount() == 0){
                    System.out.println("insufficient funds - total/transaction " + totalAmount +" /" + m.getVcAmount()
                            + " sender/recipient "+ m.getSender()+"/"+m.getRecipient());
                    return false;}
                }*/


            }

            if (!s.equals("{" + m.getId() + "}" + m.getContent())) {
                System.out.println("Signature: " + ac.decryptText(m.getSignature(), m.getPublicKey()));
                System.out.println("Content: " + "{" + m.getId() + "}" + m.getContent());
                System.out.println("Signature issue");
                return false;

            }

            //   System.out.println("funds OK - total/transaction " + totalAmount +" /" + m.getVcAmount()
            //           + " sender/recipient "+ m.getSender()+"/"+m.getRecipient());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Signature issue");

            return false;
        }
    }

    private void awardMiner(int amount, Block block) throws Exception {
        long id = this.messageId + 1;
        String sender = "BlockChain";
        String recipient = block.getCreatedBy();
        String text = "{" + id + "}" + sender + recipient + amount;
        addPendingTransaction(new Transaction(id, sender, recipient, AsymmetricCryptography.generateSignature(text, privateKey), publicKey, amount));
      //  System.out.println("Miner Awarded");
      //  System.out.println("Balance" +getBalance(recipient));
    }

    public int getBalance(String s) throws Exception {
        int totalAmount = 0;
        if (!s.equals("BlockChain")) {

            for (Block b : BlockChain.getInstance().getBlockchain()) {
                for (Transaction t : b.getTransactions()) {
                  //  System.out.println("TransactionID:" + t.getId());
                    if (t.getRecipient().equals(s)) {
                        totalAmount += t.getVcAmount();
                   //     System.out.println("RES" + t.getRecipient());
                   //     System.out.println("SEN" + s);
                   //     System.out.println("+ recipient=sender total/trans " + totalAmount + "/" + t.getVcAmount());
                    }
                    if (t.getSender().equals(s)) {
                   //     System.out.println("RES" + t.getRecipient());
                   //     System.out.println("SEN" + s);
                   //     System.out.println("- sender=sender total/trans " + totalAmount + "/" + t.getVcAmount());
                        totalAmount -= t.getVcAmount();
                    }
                 //   System.out.println("-------------------------------------------------------------------");
                }

            }
        }
        return totalAmount;
    }
}

