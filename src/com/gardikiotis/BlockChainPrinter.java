package com.gardikiotis;

public class BlockChainPrinter extends  Thread {
    BlockChain blockChain;

    public BlockChainPrinter(BlockChain blockChain) {
        this.blockChain = blockChain;
    }

    public  void printInfo() {
        for (Block b : blockChain.getBlockchain()) {
            System.out.println("Block:");
            System.out.println("Id: " + b.getId());
            System.out.println("Timestamp: " + b.getTimestamp());
            System.out.println("Magic number: " + b.getMagicNumber());
            System.out.println("Hash of the previous block: ");
            System.out.println(b.getPreviousBlockHexString());
            System.out.println("Hash of the block:");
            System.out.println(b.getBlockHex());
            System.out.print("Block was generating for ");
            System.out.format("%.3f", b.getGenerationTime());
            System.out.println(" seconds");
            System.out.println();
        }
    }

    @Override
    public synchronized void run() {
        printInfo();   }
}
