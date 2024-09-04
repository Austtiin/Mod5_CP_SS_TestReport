//Solution.CryptoClass.java
//This is the class that will handle the encryption and decryption of the input

//Austin Stephens
//Rasmussen University
//CEN4071C
//Professor Zayaz
//09/04/2024

package Solution;

import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CryptoClass {


    //Logger to log the errors
    private static final Logger LOGGER = Logger.getLogger(CryptoClass.class.getName());


    //#####KEYS#####
    private static final String AES_ALGORITHM = "AES";
    private static final String BLOWFISH_ALGORITHM = "Blowfish";
    private static final String CHACHA_ALGORITHM = "ChaCha20";
    private static final String RC4_ALGORITHM = "RC4";
    private static final byte[] AES_KEY = "PROFESSORZayass!".getBytes();
    private static final byte[] BLOWFISH_KEY = "ZayazzKey1234".getBytes();
    private static final byte[] CHACHA_KEY = "ProfessorZayas!ZayasProfessorPRO".getBytes();
    private static final byte[] CHACHA_NONCE = new byte[8];
    private static final byte[] RC4_KEY = "ProfessorZZZ".getBytes();

    private static final String DATA_FOLDER = "src/data";



    //Process file method
    //taking in the file name and the algorithm
    //produces the encrypted and decrypted content
    public static void processFile(String fileName, String algorithm) throws Exception {
        String filePath = Paths.get(DATA_FOLDER, fileName).toString();
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        String encrypted = encrypt(content, algorithm);
        String decrypted = decrypt(encrypted, algorithm);

        System.out.println("Original: " + content);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }


    //List files method
    //This method will list all the files in the data folder
    //It will produce/return a list of strings for the file names
    public static List<String> listFiles() throws Exception {
        try (Stream<Path> paths = Files.walk(Paths.get(DATA_FOLDER))) {
            return paths.filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .peek(System.out::println)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error listing files", ex);
            throw new RuntimeException("Error listing files: " + ex.getMessage());
        }
    }


    //Update file with encrypted content method
    //This method will take in the file name, the new content, and the algorithm
    //It will update the file with the new encrypted content
    public static void updateFileWithEncryptedContent(String fileName, String newContent, String algorithm) throws Exception {
        String filePath = Paths.get(DATA_FOLDER, fileName).toString();
        String validatedContent = validateInput(newContent);
        String encrypted = encrypt(validatedContent, algorithm);
        Files.write(Paths.get(filePath), encrypted.getBytes());
    }


    //Update file with decrypted content method
    //This method will take in the file name and the algorithm
    //It will update the file with the new decrypted content
    public static void updateFileWithDecryptedContent(String fileName, String algorithm) throws Exception {
        String filePath = Paths.get(DATA_FOLDER, fileName).toString();
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        String decrypted = decrypt(content, algorithm);
        Files.write(Paths.get(filePath), decrypted.getBytes());
    }


    //Encrypt method
    //This method will take in the data and the algorithm
    //It will encrypt the data and return the encrypted data
    private static String encrypt(String data, String algorithm) {

        //i will do my best to explain what is going on here
        //bring in the data and the algorithm

        //try to encrypt the data with the algorithm
        try {

            //find out if the algorithm is ChaCha20
            if (CHACHA_ALGORITHM.equals(algorithm)) {
                //ChaCha must have a nonce of 8 bytes
                if (CHACHA_NONCE.length != 8) {
                    throw new IllegalArgumentException("ChaCha20 nonce must be 8 bytes.");
                }

                //create a new ChaChaEngine with 20 rounds
                ChaChaEngine engine = new ChaChaEngine(20);

                //create a new ParametersWithIV object with the key and the nonce
                ParametersWithIV paramSpec = new ParametersWithIV(new KeyParameter(CHACHA_KEY), CHACHA_NONCE);

                //initialize the engine with the key and the nonce
                engine.init(true, paramSpec);

                //create a new byte array with the length of the data
                byte[] output = new byte[data.length()];

                //process the bytes of the data
                engine.processBytes(data.getBytes(), 0, data.length(), output, 0);

                //log the success of the encryption
                LOGGER.info("ChaCha20 encryption successful. Data length: " + data.length());

                //return the encrypted data
                return "encrypted_" + Base64.getEncoder().encodeToString(output);



            //if the algorithm is AES or Blowfish
            } else if (AES_ALGORITHM.equals(algorithm) || BLOWFISH_ALGORITHM.equals(algorithm)) {
                //create a new cipher object with the algorithm
                Cipher cipher = Cipher.getInstance(algorithm);
                //create a new key with the algorithm
                byte[] key = AES_ALGORITHM.equals(algorithm) ? AES_KEY : BLOWFISH_KEY;
                //initialize the cipher with the key
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, algorithm));
                //encrypt the data
                byte[] encryptedData = cipher.doFinal(data.getBytes());
                //log the success of the encryption
                LOGGER.info("Encryption successful. Data length: " + data.length());
                //return the encrypted data
                return "encrypted_" + Base64.getEncoder().encodeToString(encryptedData);


                //if the algorithm is RC4
            } else if (RC4_ALGORITHM.equals(algorithm)) {
                //create a new cipher object with the algorithm
                Cipher cipher = Cipher.getInstance(algorithm);
                //initialize the cipher with the key
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(RC4_KEY, algorithm));
                //encrypt the data
                byte[] encryptedData = cipher.doFinal(data.getBytes());
                //log the success of the encryption
                LOGGER.info("RC4 encryption successful. Data length: " + data.length());
                //return the encrypted data
                return "encrypted_" + Base64.getEncoder().encodeToString(encryptedData);

                //if the algorithm is not supported
            } else {
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error during encryption with algorithm: " + algorithm, ex);
            throw new RuntimeException(ex);
        }
    }



    //Decrypt method
    //This method will take in the data and the algorithm
    //It will decrypt the data and return the decrypted data
    private static String decrypt(String data, String algorithm) {

        try {
            //same deal just decrypt if the algorithm is ChaCha20
            if (CHACHA_ALGORITHM.equals(algorithm)) {
                ChaChaEngine engine = new ChaChaEngine(20);
                ParametersWithIV paramSpec = new ParametersWithIV(new KeyParameter(CHACHA_KEY), CHACHA_NONCE);
                engine.init(false, paramSpec);
                byte[] decodedData = Base64.getDecoder().decode(data.replace("encrypted_", ""));
                byte[] output = new byte[decodedData.length];
                engine.processBytes(decodedData, 0, decodedData.length, output, 0);
                LOGGER.info("ChaCha20 decryption successful. Encrypted data length: " + data.length());
                return new String(output);
            } else if (AES_ALGORITHM.equals(algorithm) || BLOWFISH_ALGORITHM.equals(algorithm)) {
                Cipher cipher = Cipher.getInstance(algorithm);
                byte[] key = AES_ALGORITHM.equals(algorithm) ? AES_KEY : BLOWFISH_KEY;
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, algorithm));
                LOGGER.info("Decryption successful. Encrypted data length: " + data.length());
                return new String(cipher.doFinal(Base64.getDecoder().decode(data.replace("encrypted_", ""))));
            } else if (RC4_ALGORITHM.equals(algorithm)) {
                Cipher cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(RC4_KEY, algorithm));
                byte[] decodedData = Base64.getDecoder().decode(data.replace("encrypted_", ""));
                LOGGER.info("RC4 decryption successful. Encrypted data length: " + data.length());
                return new String(cipher.doFinal(decodedData));
            } else {
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error during decryption with algorithm: " + algorithm, ex);
            throw new RuntimeException(ex);
        }
    }


//Validate input method
    //This method will take in the input
    //It will validate the input and return the validated input
    private static String validateInput(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\p{Punct}]", "");
    }


    //Process input method
    //This method will take in the input and the algorithm
    //It will process the input and print the original, encrypted, and decrypted content
    public void processInput(String input, String algorithm) throws Exception {
        String validatedInput = validateInput(input);
        String encrypted = encrypt(validatedInput, algorithm);
        String decrypted = decrypt(encrypted, algorithm);
        System.out.println("Original: " + validatedInput);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}