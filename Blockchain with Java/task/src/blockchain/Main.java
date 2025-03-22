package blockchain;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");
        int zeros = scanner.nextInt();
        System.out.println();

        Blockchain blockchain = new Blockchain();

        // Generate 5 blocks (including genesis block)
        for (int i = 0; i < 5; i++) {
            blockchain.generateNextBlock(zeros);
            System.out.println(blockchain.getLatestBlock());

            // Add empty line between blocks except after the last one
            if (i < 4) {
                System.out.println();
            }
        }
    }
}

class StringUtil {
    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input) {
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
    private int magicNumber;
    private long generationTime;

    public Block(int id, String previousHash, int zeros) {
        this.id = id;
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;

        // Start timing the block generation
        long startTime = System.currentTimeMillis();

        // Find a valid hash with proof of work
        this.magicNumber = findMagicNumber(zeros);
        this.hash = calculateHash();

        // Calculate generation time in seconds
        this.generationTime = (System.currentTimeMillis() - startTime) / 1000;
    }

    private int findMagicNumber(int zeros) {
        Random random = new Random();
        int magicNumber;
        String hash;
        String prefix = "0".repeat(zeros);

        do {
            magicNumber = random.nextInt(100000000);
            hash = StringUtil.applySha256(id + timestamp + previousHash + magicNumber);
        } while (!hash.startsWith(prefix));

        return magicNumber;
    }

    public String calculateHash() {
        return StringUtil.applySha256(id + timestamp + previousHash + magicNumber);
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

    public int getMagicNumber() {
        return magicNumber;
    }

    public long getGenerationTime() {
        return generationTime;
    }

    @Override
    public String toString() {
        return "Block:\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" +
                previousHash + "\n" +
                "Hash of the block:\n" +
                hash + "\n" +
                "Block was generating for " + generationTime + " seconds";
    }
}

class Blockchain {
    private List<Block> blockchain;

    public Blockchain() {
        blockchain = new ArrayList<>();
    }

    public Block generateNextBlock(int zeros) {
        if (blockchain.isEmpty()) {
            // Create genesis block with id = 1 and previousHash = "0"
            Block genesisBlock = new Block(1, "0", zeros);
            blockchain.add(genesisBlock);
            return genesisBlock;
        } else {
            Block lastBlock = getLatestBlock();
            int nextId = lastBlock.getId() + 1;
            Block newBlock = new Block(nextId, lastBlock.getHash(), zeros);
            blockchain.add(newBlock);
            return newBlock;
        }
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

    public List<Block> getBlockchain() {
        return blockchain;
    }
}