package blockchain;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();

        // Generate 4 more blocks (the genesis block was already created)
        for (int i = 0; i < 4; i++) {
            blockchain.generateNextBlock();
        }

        // Print the blockchain
        blockchain.printBlockchain();

        // Validate the blockchain
//        System.out.println("Is blockchain valid? " + blockchain.isChainValid());
    }
}

class StringUtil {
    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class Block {
    private int id;
    private long timestamp;
    private String previousHash;
    private String hash;

    public Block(int id, String previousHash) {
        this.id = id;
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(id + timestamp + previousHash);
    }

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "Block:\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Hash of the previous block:\n" +  // Ensure this is on its own line
                previousHash + "\n" +
                "Hash of the block:\n" +  // Ensure this is on its own line
                hash ;
    }
}

class Blockchain {
    private List<Block> blockchain;

    public Blockchain() {
        blockchain = new ArrayList<>();
        // Create the genesis block
        createGenesisBlock();
    }

    private void createGenesisBlock() {
        // Genesis block has id = 1 and previousHash = "0"
        Block genesisBlock = new Block(1, "0");
        blockchain.add(genesisBlock);
    }

    public Block generateNextBlock() {
        Block lastBlock = getLatestBlock();
        int nextId = lastBlock.getId() + 1;
        Block newBlock = new Block(nextId, lastBlock.getHash());
        blockchain.add(newBlock);
        return newBlock;
    }

    public Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    public boolean isChainValid() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);

            // Check if the hash of the current block is valid
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            // Check if the 'previousHash' field of the current block matches the hash of the previous block
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    public void printBlockchain() {
        for (Block block : blockchain) {
            System.out.println(block);
            System.out.println();
        }
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }
}

