package com.gardikiotis;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

class BlockChain implements Serializable {
    private static BlockChain single_instance = null;
    private ArrayList<Block> blockchain = new ArrayList<>();
    private ArrayList<String> blockchainDifficulty = new ArrayList<>();
    private volatile LinkedList<Transaction> incomingTransactions = new LinkedList<>();
    private HashBlockString hbs;
    private long TransactionId = 1L;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private int awardAmount;
    private int MaximumBlockChainCoins = 300000000;



    private BlockChain() throws Exception {

        this.awardAmount = 100; //Default award amount

            InitializeKeys();


        this.hbs = new HashBlockString(0);

    }

    private void InitializeKeys() throws NoSuchPaddingException, NoSuchAlgorithmException {
        AsymmetricCryptography ac = new AsymmetricCryptography();
        try{
            System.out.println("Enter path for BlockChain encryption keys");
            Scanner scanner = new Scanner(System.in);
        Path path = Paths.get(scanner.nextLine());
            File file=new File(path.toString());
            if (!file.exists()) {
                GenerateKeys.generateKeyPair(path.toString());
                this.privateKey = ac.getPrivate(path.toString()+"\\privateKey");
                this.publicKey = ac.getPublic(path.toString()+"\\publicKey");
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

    public static BlockChain getInstance() throws Exception {
        if (single_instance == null)
            single_instance = new BlockChain();
        return single_instance;
    }

    public static void acceptMessages(boolean b) throws Exception {
        MessageSender.generateMessages();
    }


    public HashBlockString getHbs() {
        return hbs;
    }

    public void setHbs(HashBlockString hbs) {
        this.hbs = hbs;
    }

    //BlockChain Validation
    public Boolean isValid() {
        for (int i = 0; i < blockchain.size() - 1; i++) {
            if (!blockchain.get(i + 1).getPreviousBlockHexString().equals(blockchain.get(i).getBlockHex())) {
                return false;
            }
            if (blockchain.get(i + 1).getId() != blockchain.get(i).getId() + 1) {
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
        awardMiner(awardAmount, b);
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
            this.TransactionId++;
            this.incomingTransactions.push(incomingTransaction);

        }

    }

    public synchronized List<Transaction> getPendingTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        synchronized (Miner.class) {
            while (incomingTransactions.size() > 0) {
                transactionList.add(this.incomingTransactions.remove());
            }
            return transactionList;
        }
    }

    public long getTransactionId() {
        return TransactionId;
    }


    public boolean isValidTransaction(Transaction m) {

        try {
            AsymmetricCryptography ac = new AsymmetricCryptography();
            String s = ac.decryptText(m.getSignature(), m.getPublicKey());
            if (m.getId() > TransactionId + 1) {
                System.out.println("Id issue");
                return false;
            }
            if (getBalance(m.getSender()) < m.getVcAmount() ||
                    m.getVcAmount() == 0) {
                return false;
            }

            return s.equals("{" + m.getId() + "}" + m.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void awardMiner(int amount, Block block) throws Exception {
        if (this.MaximumBlockChainCoins > 100) {
            long id = this.TransactionId + 1;
            String sender = "BlockChain";
            String recipient = block.getCreatedBy();
            String text = "{" + id + "}" + sender + recipient + amount;
            addPendingTransaction(new Transaction(id, sender, recipient, AsymmetricCryptography.generateSignature(text, this.privateKey), this.publicKey, amount));
            this.MaximumBlockChainCoins -= 100;
            return;
        }
        System.out.println("BlockChain coin limit reached");
    }

/*    public int getBalance(String s) throws Exception {

        int totalAmount = 0;
        if (s.equals("BlockChain")) {
            return this.MaximumBlockChainCoins;
        }

        for (Block b : BlockChain.getInstance().getBlockchain()) {
            for (Transaction t : b.getTransactions()) {

                if (t.getRecipient().equals(s)) {
                    totalAmount += t.getVcAmount();

                }
                if (t.getSender().equals(s)) {

                    totalAmount -= t.getVcAmount();
                }

            }

        }

        return totalAmount;
    }*/

    public long getBalance(String s) throws Exception{
        if (s.equals("BlockChain")) {
            return this.MaximumBlockChainCoins;
        }
        return Balance.apply(getAllVerifiedTransactions.apply(getBlockchain()),s);
    }

    Function<List<Block>, List<Transaction>> getAllVerifiedTransactions =
            (Function<List<Block>, List<Transaction>> & Serializable)
                    x -> x.stream().flatMap(y -> y.getTransactions().stream()).collect(Collectors.toList());

    BiFunction<List<Transaction>, String, Integer> inAmount =
            (BiFunction<List<Transaction>, String, Integer> & Serializable)
                    (x, y) -> x.stream().filter(z -> z.getRecipient().equals(y)).map(Transaction::getVcAmount).reduce(0, Integer::sum);

    BiFunction<List<Transaction>, String, Integer> outAmount =
            (BiFunction<List<Transaction>, String, Integer> & Serializable)
                    (x, y) -> x.stream().filter(z -> z.getSender().equals(y)).map(Transaction::getVcAmount).reduce(0, Integer::sum);

    BiFunction<List<Transaction>, String, Integer> Balance =
            (BiFunction<List<Transaction>, String, Integer> & Serializable)
                    (x, y) -> inAmount.apply(x, y) - outAmount.apply(x, y);

    public int getAwardAmount() {
        return awardAmount;
    }

    public void setAwardAmount(int awardAmount) {
        this.awardAmount = awardAmount;
    }
}

