// Author - Aditi Gupta (argupta@andrew.cmu.edu)
// Taken from https://www.andrew.cmu.edu/course/95-702/examples/javadoc/blockchaintask0/BlockChain.html
// Taken code from https://www.baeldung.com/java-blockchain
// Project 3 Task 0

package blockchaintask0;


import java.util.ArrayList;

public class BlockChain
        extends java.lang.Object {
     /**
     This BlockChain has exactly three instance members -
      an ArrayList to hold Blocks and a chain hash to hold a SHA256 hash of the most recently added Block.
      It also maintains an instance variable holding the approximate number of hashes per second on this computer.
      This constructor creates an empty ArrayList for Block storage.
      This constructor sets the chain hash to the empty string and sets hashes per second to 0.
*/
     private ArrayList<Block> blocks;      // To store blocks
    private String chainHash;             // To store the SHA256 hash of the most recently added block
    private long hashesPerSecond;         // To store the approximate number of hashes per second on this computer

    public BlockChain() {
        this.blocks = new ArrayList<>();
        this.chainHash = "";
        this.hashesPerSecond = 0;
    }

    /**
     * public java.lang.String getChainHash()
     * Returns:
     * the chain hash.
     */
    public java.lang.String getChainHash(){
        return chainHash;
    }

    /**public java.sql.Timestamp getTime()
     Returns:
     the current system time
        */
    public java.sql.Timestamp getTime(){
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**public blockchaintask0.Block getLatestBlock()
     Returns:
     a reference to the most recently added Block.
     */
    public Block getLatestBlock() {
        return blocks.get(blocks.size() - 1);
    }

    /**
     * computeHashesPerSecond
     * public void computeHashesPerSecond()
     * This method computes exactly 2 million hashes and times how long that process takes.
     * So, hashes per second is approximated as (2 million / number of seconds).
     * It is run on start up and sets the instance variable hashesPerSecond.
     * It uses a simple string - "00000000" to hash.
     */
    public void computeHashesPerSecond() throws Exception {
        Long startTime = System.currentTimeMillis();
        int numHash = 0;
        while (numHash < 2000000) {
            Hash.hash("00000000");
            numHash++;
        }
        Long endTime = System.currentTimeMillis();
        this.hashesPerSecond =  (int)(numHash / ((endTime - startTime) / 1000.0));
    }
    /**
     * public int getHashesPerSecond()
     * get hashes per second
     * Returns:
     * the instance variable approximating the number of hashes per second.
     */
    public int getHashesPerSecond(){
        return (int)hashesPerSecond;
    }

    /**
     * A new Block is being added to the BlockChain.
     * This new block's previous hash must hold the hash
     * of the most recently added block. After this call on addBlock,
     * the new block becomes the most recently added block on the BlockChain.
     * The SHA256 hash of every block must exhibit proof of work,
     * i.e., have the requisite number of leftmost 0's defined by its difficulty.
     * Suppose our new block is x. And suppose the old blockchain was
     * a <-- b <-- c <-- d then the chain after addBlock completes is
     * a <-- b <-- c <-- d <-- x. Within the block x, there is a previous hash field.
     * This previous hash field holds the hash of the block d.
     * The block d is called the parent of x.
     * The block x is the child of the block d.
     * It is important to also maintain a hash of the most recently added block in a chain hash.
     * Let's look at our two chains again. a <-- b <-- c <-- d.
     * The chain hash will hold the hash of d. After adding x,
     * we have a <-- b <-- c <-- d <-- x. The chain hash now holds the hash of x.
     * The chain hash is not defined within a block but is defined within the block chain.
     * The arrows are used to describe these hash pointers.
     * If b contains the hash of a then we write a <-- b.
     * Parameters:
     * newBlock is added to the BlockChain as the most recent block
     */
    public void addBlock(blockchaintask0.Block newBlock) throws Exception {
        newBlock.setPreviousHash(this.chainHash);
        this.blocks.add(newBlock);
        this.chainHash = newBlock.proofOfWork();
    }

    /**
     * public java.lang.String toString()
     * This method uses the toString method defined on each individual block.
     * Overrides:
     * toString in class java.lang.Object
     * Returns:
     * a String representation of the entire chain is returned.
     */

    /**
     * View the Blockchain
     * {"ds_chain" : [ {"index" : 0,"time stamp " : "2022-02-25 17:41:11.927","Tx ": "Genesis","PrevHash" : "","nonce" : 286,"difficulty": 2},
     * {"index" : 1,"time stamp " : "2022-02-25 17:42:46.053","Tx ": "Mike pays Marty 100 DSCoin","PrevHash" : "0026883909AA470264145129F134489316E6A38439240D0468D69AA9665D4993","nonce" : 165,"difficulty": 2},
     * {"index" : 2,"time stamp " : "2022-02-25 17:44:27.43","Tx ": "Marty pays Joe 50 DSCoin","PrevHash" : "000D14B83028DD36BD6330B8DAB185012F8625E9C9D1A8704E9C1189FD98D9DF","nonce" : 819,"difficulty": 2},
     * {"index" : 3,"time stamp " : "2022-02-25 17:45:22.044","Tx ": "Joe pays Andy 10 DSCoin","PrevHash" : "00B4CC539C5CC36AE2F09CC7B857A1330D2D02C00CA90D4A34ACD7E01D7225FC","nonce" : 167,"difficulty": 2}
     */
    //override toString method to give above output
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("{\"ds_chain\" : [");

        for (int i = 0; i < blocks.size(); i++) {
            sb.append(blocks.get(i).toString());  // This will serialize each block to JSON

            if (i != blocks.size() - 1) {  // if not the last block, append a comma
                sb.append(",");
            }
            //Code from https://stackoverflow.com/questions/14534767/how-to-append-a-newline-to-stringbuilder - for reference
            sb.append("\n");
        }
        //Code from https://stackoverflow.com/questions/22682016/how-to-use-string-builder-to-append-double-quotes-and-or-between-elements
        sb.append(" ], \"chainHash\" : ").append("\"").append(this.chainHash).append("\"").append("}");
        return sb.toString();
    }

    /**
     * public blockchaintask0.Block getBlock(int i)
     * return block at position i
     * Parameters:
     * i -
     * Returns:
     * block at postion i
     */
    public blockchaintask0.Block getBlock(int i){
        return blocks.get(i);
    }

    /**
     * public int getTotalDifficulty()
     * Compute and return the total difficulty of all blocks on the chain. Each block knows its own difficulty.
     * Returns:
     * totalDifficulty
     */
    public int getTotalDifficulty(){
        int totalDifficulty = 0;
        for (Block block : blocks) {
            totalDifficulty += block.getDifficulty();
        }
        return totalDifficulty;
    }

    /**
    * public double getTotalExpectedHashes()
    * Compute and return the expected number of hashes required for the entire chain.
    * Returns:
    * totalExpectedHashes
    */
    public double getTotalExpectedHashes(){
        double totalExpectedHashes = 0;
        for (Block block : blocks) {
            //Taken code from https://bitcoin.stackexchange.com/questions/4565/calculating-average-number-of-hashes-tried-before-hitting-a-valid-block
            totalExpectedHashes += Math.pow(16, block.getDifficulty());
        }
        return totalExpectedHashes;
    }

    /**
     * public java.lang.String isChainValid()
     * If the chain only contains one block, the genesis block at position 0,
     * this routine computes the hash of the block and
     * checks that the hash has the requisite number of leftmost 0's (proof of work) as specified in the difficulty field.
     * It also checks that the chain hash is equal to this computed hash.
     * If either check fails, return an error message. Otherwise, return the string "TRUE".
     * If the chain has more blocks than one, begin checking from block one.
     * Continue checking until you have validated the entire chain.
     * The first check will involve a computation of a hash in Block 0 and a comparison with the hash pointer in Block 1.
     * If they match and if the proof of work is correct, go and visit the next block in the chain.
     * At the end, check that the chain hash is also correct.
     * Returns:
     * "TRUE" if the chain is valid, otherwise return a string with an appropriate error message
     */

    // https://www.baeldung.com/java-blockchain - for reference
    public java.lang.String isChainValid() throws Exception {
        if (blocks.size() == 1) {
            Block block = blocks.get(0);
            String hash = block.calculateHash();
            if (hash.startsWith("0".repeat(block.getDifficulty()))) {
                if (hash.equals(this.chainHash)) {
                    return "TRUE";
                } else {
                    System.out.println("Improper hash on node 0");
                    return "FALSE";
                }
            } else {
                System.out.println("Does not begin with " + ("0".repeat(block.getDifficulty())));
                return "FALSE";
            }
        } else {
            for (int i = 1; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                Block prevBlock = blocks.get(i - 1);
                String hash = block.calculateHash();
                if (!hash.startsWith("0".repeat(block.getDifficulty())) || !block.getPreviousHash().equals(prevBlock.calculateHash())){
                        System.out.println("Improper hash on node " + i + " Does not begin with " + ("0".repeat(block.getDifficulty())));
                        return "FALSE";
                    }
            }
            return "TRUE";
        }
    }

    /**
     * public void repairChain()
     * This routine repairs the chain.
     * It checks the hashes of each block and ensures that any illegal hashes are recomputed.
     * After this routine is run, the chain will be valid.
     * The routine does not modify any difficulty values.
     * It computes new proof of work based on the difficulty specified in the Block.
     */
    public void repairChain() throws Exception {
        String hash = "";
        for (int i = 1; i < this.blocks.size(); i++) {
            Block currentBlock = this.blocks.get(i);
            Block prevBlock = this.blocks.get(i - 1);
            //Setting all the hashes again so that the chain is repaired
            currentBlock.setPreviousHash(prevBlock.calculateHash());

            // Getting the proof of work for the current block
            hash=currentBlock.proofOfWork();
        }
        //Assigning the proof of work of the final block to the chain hash
        this.chainHash = hash;
    }

    /**
     * public static void main(java.lang.String[] args)
     * This routine acts as a test driver for your Blockchain.
     * It will begin by creating a BlockChain object and then adding the Genesis block to the chain.
     * The Genesis block will be created with an empty string as the pervious hash and a difficulty of 2.
     * On start up, this routine will also establish the hashes per second instance member.
     * All blocks added to the Blockchain will have a difficulty passed in to the program by the user at run time.
     * All hashes will have the proper number of zero hex digits representing the most significant nibbles in the hash.
     * A nibble is 4 bits. If the difficulty is specified as 3, then all hashes will begin with 3 or more 0 hex digits (or 3 nibbles, or 12 zero bits).
     *
     * It is menu driven and will continously provide the user with seven options:
     * Block Chain Menu
     * 0. View basic blockchain status.
     * 1. Add a transaction to the blockchain.
     * 2. Verify the blockchain.
     * 3. View the blockchain.
     * 4. Corrupt the chain.
     * 5. Hide the corruption by repairing the chain.
     * 6. Exit.
     *
     * If the user selects option 0, the program will display:
     * The number of blocks on the chain
     * Difficulty of most recent block
     * The total difficulty for all blocks Approximate hashes per second on this machine.
     * Expected total hashes required for the whole chain.
     * The computed nonce for most recent block.
     * The chain hash (hash of the most recent block).
     *
     * If the user selects option 1,
     * the program will prompt for and then read the difficulty level for this block.
     * It will then prompt for and then read a line of data from the user (representing a transaction).
     * The program will then add a block containing that transaction to the block chain.
     * The program will display the time it took to add this block.
     * Note: The first block added after Genesis has index 1.
     * The second has 2 and so on. The Genesis block is at position 0.
     *
     * If the user selects option 2, then call the isChainValid method and display the results.
     * It is important to note that this method will execute fast.
     * Blockchains are easy to validate but time consuming to modify.
     * Your program needs to display the number of milliseconds it took for validate to run.
     *
     * If the user selects option 3, display the entire Blockchain contents as a correctly formed JSON document.
     * See www.json.org.
     *
     * If the user selects option 4, she wants to corrupt the chain.
     * Ask her for the block index (0..size-1) and ask her for the new data that will be placed in the block.
     * Her new data will be placed in the block. At this point, option 2 (verify chain) should show false.
     * In other words, she will be making a data change to a particular block and the chain itself will become invalid.
     *
     * If the user selects 5,
     * she wants to repair the chain.
     * That is, she wants to recompute the proof of work for each node that has become invalid - due perhaps,
     * to an earlier selection of option 4. The program begins at the Genesis block and checks each block in turn.
     * If any block is found to be invalid, it executes repair logic.
     *
     * Important:
     *
     * Within your comments in the main routine, you must describe how this system behaves as the difficulty increases.
     * Run some experiments by adding new blocks with increasing difficulties.
     * Describe what you find. Be specific and quote some times.
     *
     * You need not employ a system clock.
     * You should be able to make clear statements describing the approximate run times
     * associated with addBlock(), isChainValid(), and chainRepair().
     *
     * Parameters:
     * args - is unused
     */
    public static void main(java.lang.String[] args) throws Exception {
        Long startTime, endTime;
        double elapsedTimeInMilliSeconds;
        BlockChain blockChain = new BlockChain();
        Block genesisBlock = new Block(0, blockChain.getTime(), "Genesis", 2);
        blockChain.addBlock(genesisBlock);
        blockChain.computeHashesPerSecond();
        while(true) {
            System.out.println("0. View basic blockchain status.");
            System.out.println("1. Add a transaction to the blockchain.");
            System.out.println("2. Verify the blockchain.");
            System.out.println("3. View the blockchain.");
            System.out.println("4. Corrupt the chain.");
            System.out.println("5. Hide the corruption by repairing the chain.");
            System.out.println("6. Exit.");
            System.out.println("Enter a number between 0 and 6:");
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int input = scanner.nextInt();
            while (input != 7) {
                if (input == 0) {
                    System.out.println("Current size of chain: " + blockChain.blocks.size());
                    System.out.println("Difficulty of most recent block: " + blockChain.getLatestBlock().getDifficulty());
                    System.out.println("Total difficulty for all blocks: " + blockChain.getTotalDifficulty());
                    System.out.println("Approximate hashes per second on this machine: " + blockChain.getHashesPerSecond());
                    System.out.println("Expected total hashes required for the whole chain: " + blockChain.getTotalExpectedHashes());
                    System.out.println("Nonce for most recent block: " + blockChain.getLatestBlock().getNonce());
                    System.out.println("Chain hash: " + blockChain.getChainHash());
                    break;
                } else if (input == 1) {
                    System.out.println("Enter difficulty > 0");
                    int difficulty = scanner.nextInt();
                    System.out.println("Enter transaction");

                    //After nextInt only the first word is read, so we need to use nextLine to read the entire line
                    //Code taken from https://stackoverflow.com/questions/39514730/how-to-take-input-as-string-with-spaces-in-java-using-scanner
                    scanner.nextLine();
                    String transaction = scanner.nextLine();
                    Block block = new Block(blockChain.blocks.size(), blockChain.getTime(), transaction, difficulty);
                     startTime = System.currentTimeMillis();
                    blockChain.addBlock(block);
                     endTime = System.currentTimeMillis();
                     elapsedTimeInMilliSeconds = (endTime - startTime);
                    System.out.println("Total execution time to add this block was " + elapsedTimeInMilliSeconds + " milliseconds");
                    break;

                    // Time required to add a block increases as the difficulty increases.
                    // It increases exponentially as the difficulty increases.
                    // From difficulty 2 to 4 the increase in time required is less (in magnitude of 10s).
                    // But from difficulty 4 to 5 the increase in time required is high (in the magnitude of 1000s).
                    // And from difficulty 5 to 6 the increase in time required is even higher (in the magnitude of 10000s).

                    // For instance:
                    // Time required to add a block of difficulty 2 was between 1-10 milliseconds
                    // Time required to add a block of difficulty 3 was 27 milliseconds
                    // Time required to add a block of difficulty 4 was 39 milliseconds
                    // Time required to add a block of difficulty 5 was 1432 milliseconds
                    // Time required to add a block of difficulty 6 was 18296 milliseconds

                } else if (input == 2) {
                     startTime = System.currentTimeMillis();
                    System.out.println("Chain verification: " + blockChain.isChainValid());
                     endTime = System.currentTimeMillis();
                     elapsedTimeInMilliSeconds = (endTime - startTime);
                    System.out.println("Total execution time required to verify the chain was " + elapsedTimeInMilliSeconds + " milliseconds");
                    break;
                    // Time required to verify the chain of any length and any difficulty was between 1-10 milliseconds
                    // It was mostly 0 milliseconds in most cases

                } else if (input == 3) {
                    System.out.println("View the Blockchain");
                    System.out.println(blockChain.toString());
                    break;
                } else if (input == 4) {
                    System.out.println("Corrupt the Blockchain");
                    System.out.println("Enter block ID of block to Corrupt:");
                    int blockId = scanner.nextInt();
                    System.out.println("Enter new data for block " + blockId);

                    //After nextInt only the first word is read, so we need to use nextLine to read the entire line
                    //Code taken from https://stackoverflow.com/questions/39514730/how-to-take-input-as-string-with-spaces-in-java-using-scanner
                    scanner.nextLine();
                    String data = scanner.nextLine();
                    Block block = blockChain.getBlock(blockId);
                    block.setData(data);
                    System.out.println("Block " + blockId + " now holds " + data);
                    break;
                } else if (input == 5) {
                    System.out.println("Repairing the entire chain");
                     startTime = System.currentTimeMillis();
                     blockChain.repairChain();
                     endTime = System.currentTimeMillis();
                    elapsedTimeInMilliSeconds = (endTime - startTime);
                    System.out.println("Total execution time required to repair the chain was " + elapsedTimeInMilliSeconds + "milliseconds");
                    break;

                    // Time required to repair the chain increased with increasing difficulty of the most recent block.
                    // It increases exponentially as the difficulty increases.
                    // For instance:
                    // Time required to repair the chain with the most recent block of difficulty 2 was 7 milliseconds
                    // Time required to repair the chain with the most recent block of difficulty 3 was 18 milliseconds
                    // Time required to repair the chain with the most recent block of difficulty 4 was 27 milliseconds
                    // Time required to repair the chain with the most recent block of difficulty 5 was 1443 milliseconds
                    //Time required to repair the chain with the most recent block of difficulty 6 was 8712 milliseconds

                } else if (input == 6) {
                    System.exit(0);
                }

            }
        }
    }
}

