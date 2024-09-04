//homeFrame.java
//This is the main home frame of the application

//This is more of the GUI side of the application and is where the user will interact with the application
//homeFrame.java
//This is the main home frame of the application

//This is more of the GUI side of the application and is where the user will interact with the application

//we will show the MD5 hash for the GenerateTextFile.java file here as a button to click.
//This will be the code word for the live classroom.


//Austin Stephens
//Rasmussen University
//CEN4071C
//Professor Zayaz
//09/04/2024

package App;

import Solution.CryptoClass;
import Solution.timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class homeFrame extends JFrame {

    private final JTextArea displayArea;


    //This is the main home frame of the application
    public homeFrame() {
        CryptoClass crypto = new CryptoClass();
        timer Timer = new timer();

        setTitle("Encryption/Decryption");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color backgroundColor = new Color(18, 18, 18);
        Color buttonColor = new Color(30, 215, 96);
        Color textColor = Color.WHITE;
        Font font = new Font("Proxima Nova", Font.BOLD, 16);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        displayArea = new JTextArea();
        displayArea.setBackground(backgroundColor);
        displayArea.setForeground(textColor);
        displayArea.setFont(font);
        displayArea.setEditable(false);


        //This is the main home frame of the application
        JButton fileEncryptButton = getJButton("Encrypt from file", buttonColor, textColor, font);
        JButton fileDecryptButton = getJButton("Decrypt from file", buttonColor, textColor, font);
        JButton inputButton = getJButton("Encrypt/Decrypt from keyboard input", buttonColor, textColor, font);
        JButton exitButton = getJButton("Exit", buttonColor, textColor, font);
        JButton reportTestButton = getJButton("Run Report Test Case", buttonColor, textColor, font);
        JButton md5HashButton = getJButton("Generate MD5 Hash", buttonColor, textColor, font);


        //fileEncryptButton
        //This will allow the user to encrypt a file
        //Button add action listener to encrypt a file
        fileEncryptButton.addActionListener(e -> {
            try {
                // Retrieve the list of files
                List<String> files = CryptoClass.listFiles();

                // Prompt the user to select a file
                String fileName = (String) JOptionPane.showInputDialog(null, "Choose file:", "File Selection",
                        JOptionPane.QUESTION_MESSAGE, null, files.toArray(), files.get(0));

                if (fileName != null) {
                    // Prompt the user to select an encryption algorithm
                    String algorithm = (String) JOptionPane.showInputDialog(null, "Choose algorithm:", "Algorithm Selection",
                            JOptionPane.QUESTION_MESSAGE, null, new String[]{"AES", "Blowfish", "ChaCha20", "RC4"}, "AES");

                    // Encrypt the selected file using the chosen algorithm
                    CryptoClass.updateFileWithEncryptedContent(fileName, algorithm);

                    // Update the display area with a success message
                    displayArea.setText("File " + fileName + " has been updated with encrypted content.");
                }
            } catch (Exception ex) {
                // Handle any exceptions and display an error message
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });


        //fileDecryptButton
        //This will allow the user to decrypt a file
        fileDecryptButton.addActionListener(e -> {


            try {

                // Retrieve the list of files
                List<String> files = CryptoClass.listFiles();
                String fileName = (String) JOptionPane.showInputDialog(null, "Choose file:", "File Selection",
                        JOptionPane.QUESTION_MESSAGE, null, files.toArray(), files.get(0));

                if (fileName != null) {
                    String algorithm = (String) JOptionPane.showInputDialog(null, "Choose algorithm:", "Algorithm Selection",
                            JOptionPane.QUESTION_MESSAGE, null, new String[]{"AES", "Blowfish", "ChaCha20", "RC4"}, "AES");

                    CryptoClass.updateFileWithDecryptedContent(fileName, algorithm);
                    displayArea.setText("File " + fileName + " has been updated with decrypted content.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });


        //inputButton
        //This will allow the user to encrypt/decrypt text input
        inputButton.addActionListener(e -> {
            try {
                String input = JOptionPane.showInputDialog("Enter text to encrypt:");
                String algorithm = (String) JOptionPane.showInputDialog(null, "Choose algorithm:", "Algorithm Selection",
                        JOptionPane.QUESTION_MESSAGE, null, new String[]{"AES", "Blowfish", "ChaCha20", "RC4"}, "AES");

                crypto.processInput(input, algorithm);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });


        //exitButton
        exitButton.addActionListener(e -> System.exit(0));


        //reportTestButton
        //this was added week 5 to test the encryption time of the different algorithms
        reportTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int runs = 20;
                    String[] algorithms = {"AES", "Blowfish", "ChaCha20", "RC4"};
                    StringBuilder report = new StringBuilder();
                    File largeFile = new File("src/data/10mb-examplefile-com.txt");
                    String fileContent = new String(Files.readAllBytes(largeFile.toPath()));
                    timer Timer = new timer();

                    for (String algorithm : algorithms) {
                        long totalEncryptTime = 0;
                        for (int i = 0; i < runs; i++) {
                            Timer.start();
                            crypto.processInput(fileContent, algorithm);
                            Timer.stop();
                            long encryptTime = Timer.getDuration();
                            totalEncryptTime += encryptTime;
                            System.out.println("Algorithm: " + algorithm + " | Run " + (i + 1) + " Encryption Time: " + encryptTime + " ms");
                        }
                        long avgEncryptTime = totalEncryptTime / runs;
                        Timer.addAverageTime(avgEncryptTime, algorithm);
                        report.append("Algorithm: ").append(algorithm)
                                .append("\nAverage Encryption Time: ").append(avgEncryptTime).append(" ms\n\n");
                        System.out.println("Algorithm: " + algorithm + " | Average Encryption Time: " + avgEncryptTime + " ms");
                    }
                    Files.write(Paths.get("src/data/reportResults.txt"), report.toString().getBytes());
                    displayArea.setText(report.toString());
                    Timer.printAverageTimes(runs);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        md5HashButton.addActionListener(e -> {
            try {
                File file = new File("src/data/GenerateTextFile.java");
                String fileContent = new String(Files.readAllBytes(file.toPath()));
                String md5Hash = generateMD5Hash(fileContent);
                displayArea.setText("MD5 Hash of GenerateTextFile.java:\n" + md5Hash);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        panel.add(fileEncryptButton);
        panel.add(fileDecryptButton);
        panel.add(inputButton);
        panel.add(exitButton);
        panel.add(reportTestButton);
        panel.add(md5HashButton); // Add new button to panel

        add(panel, BorderLayout.CENTER);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        setVisible(true);
    }

    //This will get the button
    private JButton getJButton(String text, Color backgroundColor, Color foregroundColor, Font font) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setFont(font);
        button.setFocusPainted(false);
        return button;
    }

    //for week 4 code word
    private String generateMD5Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    //this is just the main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(homeFrame::new);
    }


}