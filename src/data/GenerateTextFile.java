package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateTextFile {

    public static void main(String[] args) {
        generateTextFile("testFile.txt", 10 * 1024 * 1024); // 10MB
    }

    private static void generateTextFile(String fileName, long fileSize) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            long remainingBytes = fileSize;

            while (remainingBytes > 0) {
                int chunkSize = (int) Math.min(remainingBytes, 1024);
                String randomChunk = generateRandomString(chunkSize);
                writer.write(randomChunk);
                remainingBytes -= chunkSize;
            }

            System.out.println("File generated successfully: " + fileName);
			writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
    }

    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }
}
