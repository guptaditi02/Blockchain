// Author - Aditi Gupta (argupta@andrew.cmu.edu)
// Taken from https://www.andrew.cmu.edu/course/95-702/examples/javadoc/blockchaintask0/Block.html
// Taken code from https://www.baeldung.com/java-blockchain
// Project 3 Task 0

package blockchaintask0;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

/**
 * This class represents a simple Block.
 *
 * Each Block object has an index - the position of the block on the chain.
 * The first block (the so called Genesis block) has an index of 0.
 *
 * Each block has a timestamp - a Java Timestamp object, it holds the time of the block's creation.
 *
 * Each block has a field named data - a String holding the block's single transaction details.
 *
 * Each block has a String field named previousHash - the SHA256 hash of a block's parent.
 * This is also called a hash pointer.
 *
 * Each block holds a nonce - a BigInteger value determined by a proof of work routine.
 * This has to be found by the proof of work logic.
 * It has to be found so that this block has a hash of the proper difficulty.
 * The difficulty is specified by a small integer representing the minimum number of leading hex zeroes the hash must have.
 *
 * Each block has a field named difficulty - it is an int that specifies the minimum number of left most hex digits needed by a proper hash.
 * The hash is represented in hexadecimal. If, for example, the difficulty is 3, the hash must have at least three leading hex 0's (or,1 and 1/2 bytes).
 * Each hex digit represents 4 bits.
 */
public class Block extends java.lang.Object{
    private int index;
    private java.sql.Timestamp timestamp;
    @SerializedName(value = "Tx")
    private java.lang.String data;
    @SerializedName(value = "PrevHash")
    private java.lang.String previousHash;
    private BigInteger nonce = BigInteger.ZERO;
    private int difficulty;

    /**
     * This the Block constructor.
     *
     * Parameters:
     * index - This is the position within the chain. Genesis is at 0.
     * timestamp - This is the time this block was added.
     * data - This is the transaction to be included on the blockchain.
     * difficulty - This is the number of leftmost nibbles that need to be 0.
     */
    public Block(int index, java.sql.Timestamp timestamp, java.lang.String data, int difficulty){
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.difficulty = difficulty;
    }

    /**
     * This method computes a hash of the concatenation of the index, timestamp, data,
     * previousHash, nonce, and difficulty.
     *
     * Returns: a String holding Hexadecimal characters
     */

    public java.lang.String calculateHash() throws Exception {
        String total = "";
        total+=String.valueOf(this.index);

        // Code taken from https://www.tutorialspoint.com/java-sql-timestamp-tostring-method-with-example#:~:text=The%20toString()%20method%20of,Timestamp%20object%20to%20a%20String.
        total+=this.timestamp.toString();
        total+=this.data;
        total+=this.previousHash;
        total+=String.valueOf(this.nonce);
        total+=String.valueOf(this.difficulty);
        String hash=Hash.hash(total);
        return hash;
    }

/**
 * This method returns the nonce for this block.
 * The nonce is a number that has been found to cause the hash of this block
 * to have the correct number of leading hexadecimal zeroes.
 *
 * Returns: a BigInteger representing the nonce for this block.
 */
    public BigInteger getNonce() {
        return this.nonce;
    }

/**
 * The proof of work methods finds a good hash. It increments the nonce until it produces a good hash.
 *
 * This method calls calculateHash() to compute a hash of the concatenation of the index, timestamp, data,
 * previousHash, nonce, and difficulty. If the hash has the appropriate number of leading hex zeroes,
 * it is done and returns that proper hash.
 * If the hash does not have the appropriate number of leading hex zeroes,
 * it increments the nonce by 1 and tries again. It continues this process, burning electricity and CPU cycles,
 * until it gets lucky and finds a good hash.
 *
 * Returns:
 * a String with a hash that has the appropriate number of leading hex zeroes.
 * The difficulty value is already in the block. This is the minimum number of hex 0's a proper hash must have.
 */

    // Taken  code from https://stackoverflow.com/questions/66649182/not-able-to-get-4-leading-zeros-in-sha256-hash-proof-of-work-ever-java
    public java.lang.String proofOfWork() throws Exception {
        String hash = "";

        //This line of code suggested by GitHub Copilot
        while(!hash.startsWith("0".repeat(this.difficulty))){
            this.nonce = this.nonce.add(BigInteger.ONE);
            hash = calculateHash();
        }
        return hash;
    }

    /**
     * Simple getter method
     * Returns: difficulty of this block
     */

    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Simple setter method
     *
     * Parameters:
     * difficulty - determines how much work is required to produce a proper hash
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**Override Java's toString method
     * Overrides:
     * toString in class java.lang.Object
     * Returns:
     * A JSON representation of all of this block's data is returned.
        */
    public java.lang.String toString(){
        // Create a Gson object
        Gson gson = new Gson();
        // Serialize to JSON
        return gson.toJson(this);
    }


    /**
     * Simple setter method
     * Parameters:
     * previousHash - a hashpointer to this block's parent
     */
    public void setPreviousHash(String previousHash){
        this.previousHash = previousHash;
    }

    /**Simple getter method

     Returns:
     previous hash
     */
    public java.lang.String getPreviousHash(){
        return this.previousHash;
    }

    /**
     * Simple getter method
     * Returns: index of block
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * Simple setter method
     * Parameters:
     * index - the index of this block in the chain
     */
    public void setIndex(int index){
        this.index = index;
    }

    /**
     * Simple setter method
     * Parameters:
     * timestamp - of when this block was created
     */
    public void setTimestamp(java.sql.Timestamp timestamp){
        this.timestamp = timestamp;
    }

    /**
     * Simple getter method
     * Returns:
     * timestamp of this block
     */
    public java.sql.Timestamp getTimestamp(){
        return this.timestamp;
    }

    /**
     * Simple getter method
     * Returns:
     * this block's transaction
     */
    public java.lang.String getData(){
        return this.data;
    }

    /**
     * Simple setter method
     * @param data represents the transaction held by this block
     */
    public void setData(java.lang.String data){
        this.data = data;
    }

    public static void main(java.lang.String[] args){
    }

}
