//main.java
//This is where we will have the main method to run the program

//Austin Stephens
//Rasmussen University
//CEN4071C
//Professor Zayaz
//09/04/2024

//This is the main class that will run the program
//We have moved over our code and cleaned it up to make it more readable.


/*
Extend your Java program so that it also:



Calculates how many milliseconds it takes to encrypt a large text file using AES
Run this test 20 times and report the average


Calculates how many milliseconds it takes to encrypt a large text file using Blowfish
Run this test 20 times and report the average


Calculates how many milliseconds it takes to encrypt a large text file using RC4
Run this test 20 times and report the average


The text file should be 10MB or large, and this same file is to be used with every test run


Uses System.currentTimeMillis() method in a “stopwatch” style test scheme
*/


//This week we are going to add a stopwatch to our program to calculate the time it takes to encrypt a large text file using AES, Blowfish, and RC4.
//This class will be universal so we can use it in our CryptoClass.java file.

//For our live classroom code word we will find the MD5 hash for the GenerateTextFile.java
// that you are to download and use for the Module 5 Course Project Assignment.


package Solution;
import App.homeFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {



            //start main frame of the application
            //set to visible
            homeFrame mainFrame = new homeFrame();
            mainFrame.setVisible(true);
        });
    }
}