//homeFrame.java
//This is the main home frame of the application

//This is more of the GUI side of the application and is where the user will interact with the application
//homeFrame.java
//This is the main home frame of the application

//This is more of the GUI side of the application and is where the user will interact with the application


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
                    CryptoClass.updateFileWithEncryptedContent(fileName, fileName, algorithm);

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

            //This will test the encryption time of the different algorithms
            public void actionPerformed(ActionEvent e) {

                //try and run the test
                try {
                    int runs = 20;
                    // Fixed number of runs as per requirements

                    String[] algorithms = {"AES", "Blowfish", "ChaCha20", "RC4"};
                    StringBuilder report = new StringBuilder();

                    //large data of 10MB
                    File largeFile = new File("src/data/10mb-examplefile-com.txt");
                    String fileContent = new String(Files.readAllBytes(largeFile.toPath()));

                    // Run the test for each algorithm
                    for (String algorithm : algorithms) {
                        long totalEncryptTime = 0;


                        //run the test for each algorithm
                        for (int i = 0; i < runs; i++) {
                            // Measure encryption time
                            timer Timer = new timer();
                            Timer.start();
                            crypto.processInput(fileContent, algorithm);
                            Timer.stop();
                            long encryptTime = Timer.getDuration();
                            totalEncryptTime += encryptTime;

                            //append the report to the string
                            report.append("Algorithm: ").append(algorithm)
                                    .append("\nRun ").append(i + 1).append(" Encryption Time: ").append(encryptTime).append(" ms\n");
                        }

                        // Calculate the average encryption time
                        long avgEncryptTime = totalEncryptTime / runs;


                        //append the report to the string
                        report.append("Algorithm: ").append(algorithm)
                                .append("\nAverage Encryption Time: ").append(avgEncryptTime).append(" ms\n\n");
                    }

                    // Output the report to a file
                    Files.write(Paths.get("src/data/reportResults.txt"), report.toString().getBytes());

                    // Display the report in the display area
                    displayArea.setText(report.toString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });


        //adding buttons here
        panel.add(fileEncryptButton);
        panel.add(fileDecryptButton);
        panel.add(inputButton);
        panel.add(exitButton);
        panel.add(reportTestButton);

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


    //this is just the main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(homeFrame::new);
    }
}