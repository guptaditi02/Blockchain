// Author - Aditi Gupta (argupta@andrew.cmu.edu)
// Taken from Lab 1: https://github.com/CMU-Heinz-95702/Lab1-InstallationAndRaft
// Project 3 Task 0

package blockchaintask0;
import java.security.MessageDigest;
public class Hash {

    public static String hash(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(s.getBytes());
        return bytesToHex(md.digest());
    }

    // Code from stack overflow
    // https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    // Returns a hex string given an array of bytes
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
