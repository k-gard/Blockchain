# Blockchain
Basic Blockchain implementation

## Description

Blockchain has a simple interpretation: it's just a chain of blocks. It represents a sequence of data that you can't break in the middle; you can only append new data at the end of it. All the blocks in the blockchain are chained together.


  
To be called a blockchain, every block must include the  **hash of the previous block**. Other fields of the block are optional and can store various information. The hash of a block is  **a hash of all fields** **of a block**. 
If you change one block in the middle, the hash of this block will also change. and the next block in the chain would no longer contain the hash of the previous block. Therefore, it’s easy to check that the chain is invalid.  
  
The chain starts with a block whose id = 1. 

Also, every block contains a timestamp representing the time the block was created. 

Since the first block doesn't have a previous one, its hash of the previous block should be 0.

The class Blockchain  validates all the blocks in the blockchain and returns true if the blockchain is valid. 

For hashing blocks,the cryptographic hash function which is used is **SHA-256** hashing








-----------------------------------------------------------
## Security

  
 **proof of work**.   
The main goal is that the hash of the block is not random. It should start with some amount of zeros. To achieve that, the block contains the field:   **magic number**. This number takes part in calculating the hash of this block. With one magic number, and with another, the hashes would be totally different even though the other part of the block stays the same. But with the help of probability theory, we can say that there exist some magic numbers, with which the hash of the block starts with some number of zeros. The only way to find one of them is to make random guesses until we found one of them. For a computer, this means that the only way to find the solution is to brute force it: try 1, 2, 3, and so on. The better solution would be to brute force with random numbers, not with the increasing from 1 to N where N is the solution. You can see this algorithm in the animation below:

![](https://ucarecdn.com/6c578e14-8e6c-43fc-b67f-81a46405b7d8/)

Obviously, the more zeros you need at the start of the block hash, the harder this task will become. And finally, if a hacker wants to change some information in the middle of the blockchain, the hash of the modified block would be changed and it won't start with zeros, so he would be forced to find another magic number to create a block with a hash which starts with zeros. Note that the hacker must find magic numbers for all of the blocks until the end of the blockchain, which seems like a pretty impossible task, considering that the blockchain will grow faster.

It's said that that the block is  **proved** if it has a hash which starts with some number of zeros. The information inside it is impossible to change even though the information itself is open and easy to edit in the text editor. The result of the edit is a changed hash of the block, no longer containing zeros at the start, so this block suddenly becomes  **unproved** after the edit. And since the blockchain must consist of only proved blocks, the whole blockchain becomes invalid. This is the power of the **proof of work** concept.  
  
The blockchain generates new blocks only with hashes that start with **N** zeros. The number N should be input from the keyboard. Also, the blockchain should be saved to the file after each block. At the start of the program, you should check if a blockchain exists on the hard drive, load it, check if it is valid, and then continue to create blocks. You may want to use serialization to do that.









----------------------------------------------------------------

## **Block Generation : Mining**

The blockchain just keeps the chain valid and accepts the new blocks from outside. In the outside world, there are a lot of computers that try to create a new block. All they do is search for a magic number to create a block whose hash starts with some zeros. The first computer to do so is a winner, the blockchain accepts this new block, and then all these computers try to find a magic number for the next block.  
  
There is a special word for this:  **mining**. The process of mining blocks is hard work for computers, like the process of mining minerals in real life is hard work. Computers that perform this task are called  **miners**.  
  
Note that if there are more miners, the new blocks will be mined faster. But the problem is that we want to create new blocks with a stable frequency. For this reason, the blockchain regulates the number **N**: the number of zeros at the start of a hash of the new block. If suddenly there are so many miners that the new block is created in a matter of seconds, the complexity of the next block is increased by increasing the number N. On the other hand, if there are so few miners that process of creating a new block takes longer than a minute, the number N is lowered.  
  
This application uses  threads with miners, and every one of them contains the same blockchain. The miners  mine new blocks and the blockchain should regulates the number N. The blockchain checks the validity of the incoming block (ensure that the previous hash equals the hash of the last block of the blockchain and the hash of this new block starts with N zeros). At the start, the number N equals 0 and is increased by 1 / decreased by 1 / stays the same after the creation of the new block based on the time of its creation.



------------------------------------------------------



## Data

The most useful information in the blockchain is the data that every block stores. The information can be anything. 

Today, the most common application of blockchains is cryptocurrencies. A cryptocurrency’s blockchain contains a list of transactions: everyone can see the transactions but no one is able to change them. In addition, no one can send a transaction as another person; this is possible using digital signatures. 
  
A miner who creates a new block is awarded some virtual money, for example, 100 virtual coins. This can be remembered in the blockchain if the block stores information about the miner who created this block. 
  
After that, a miner can spend these 100 virtual coins by giving them to someone else. In the real world, he can buy things and pay for them using these virtual coins instead of real money. These virtual coins go to the company that sells the things, and the company can pay salaries with these virtual coins. The circulation of these coins starts here and suddenly the virtual coins become more popular than real money!  
  
To check how many coins a person has, all of his transactions and all of the transactions to him, assuming that the person started with zero virtual coins is checked. The transaction is rejected when the person tries to spend more money than he has at the moment. Create a special method that returns how many coins the person has.  
  


## Output example

In the output example, VC stands for Virtual Coins. T
```
Block:  
Created by: miner9  
miner9 gets 100 VC  
Id: 1  
Timestamp: 1539866031047  
Magic number: 76384756  
Hash of the previous block:  
0  
Hash of the block:  
1d12cbbb5bfa278734285d261051f5484807120032cf6adcca5b9a3dbf0e7bb3  
Block data:  
No transactions  
Block was generating for  0 seconds  
N was increased to 1  
  
Block:  
Created by: miner7  
miner7 gets 100 VC  
Id: 2  
Timestamp: 1539866031062  
Magic number: 92347234  
Hash of the previous block:  
1d12cbbb5bfa278734285d261051f5484807120032cf6adcca5b9a3dbf0e7bb3  
Hash of the block:  
04a6735424357bf9af5a1467f8335e9427af714c0fb138595226d53beca5a05e  
Block data:  
miner9 sent 30 VC to miner1  
miner9 sent 30 VC to miner2  
miner9 sent 30 VC to Nick  
Block was generating for  0 seconds  
N was increased to 2  
  
Block:  
Created by: miner1  
miner1 gets 100 VC  
Id: 3  
Timestamp: 1539866031063  
Magic number: 42374628  
Hash of the previous block:  
04a6735424357bf9af5a1467f8335e9427af714c0fb138595226d53beca5a05e  
Hash of the block:  
0061924d48d5ce30e97cfc4297f3a40bc94dfac6af42d7bf366d236007c0b9d3  
Block data:  
miner9 sent 10 VC to Bob  
miner7 sent 10 VC to Alice  
Nick sent 1 VC to ShoesShop  
Nick sent 2 VC to FastFood  
Nick sent 15 VC to CarShop  
miner7 sent 90 VC to CarShop  
Block was generating for  0 seconds  
N was increased to 3  
  
Block:  
Created by miner2  
miner2 gets 100 VC  
Id: 4  
Timestamp: 1539866256729  
Magic number: 45382978  
Hash of the previous block:  
0061924d48d5ce30e97cfc4297f3a40bc94dfac6af42d7bf366d236007c0b9d3  
Hash of the block:  
000856a20d767fbbc38e0569354400c1750381100984a09a5d8b1cdf09b0bab6  
Block data:  
CarShop sent 10 VC to Worker1  
CarShop sent 10 VC to Worker2  
CarShop sent 10 VC to Worker3  
CarShop sent 30 VC to Director1  
CarShop sent 45 VC to CarPartsShop  
Bob sent 5 VC to GamingShop  
Alice sent 5 VC to BeautyShop  
Block was generating for  5 seconds  
N was increased to 4
```
