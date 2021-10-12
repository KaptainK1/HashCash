import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class SHAUtils {

    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static byte[] digest(byte[] data, String algorithm){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
//            e.printStackTrace();
        }
        return md.digest(data);
    }

    public static String bytesToHex(byte[] data){
        StringBuilder hexString = new StringBuilder(2 * data.length);
        for (int i = 0; i < data.length ; i++) {
            String hex = Integer.toHexString(0xff & data[i]);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();

    }

    public static byte[] concatenateHash(byte[] leftHash, byte[] rightHash, String algorithm){

        //create three BigIntegers, 2 to store the hashes from the input and
        //1 to store the combined has as an int
        BigInteger leftHashInt = new BigInteger(leftHash);
        BigInteger rightHashInt = new BigInteger(rightHash);

//        System.out.println("result: " + leftHashInt+rightHashInt);

        BigInteger finalHashInt = leftHashInt.add(rightHashInt);

        return SHAUtils.digest(finalHashInt.toByteArray(), algorithm);
    }

    public static void main(String[] args) throws IOException {

        String alg = "SHA-256";

        String text = "323";
        String text1 = "465";



        System.out.println("Input is: " + text + " length is " + text.length());

        byte[] shaInBytes = SHAUtils.digest(text.getBytes(UTF_8), alg);
        byte[] shaInBytes1 = SHAUtils.digest(text1.getBytes(UTF_8), alg);

        System.out.println("hex value is " + bytesToHex(shaInBytes) + " length is " + shaInBytes.length);
        System.out.println("hex value is " + bytesToHex(shaInBytes1) + " length is " + shaInBytes1.length);

        BigInteger firstInt = new BigInteger(shaInBytes);
        BigInteger secondInt = new BigInteger(shaInBytes1);

        BigInteger finalInt = firstInt.add(secondInt);
        System.out.println("Big int is " + finalInt);

        byte[] finalHash = SHAUtils.digest(finalInt.toByteArray(), alg);

        System.out.println("hex value is " + bytesToHex(finalHash) + " length is " + finalHash.length);
        System.out.println(finalInt.bitCount());

    }




}
