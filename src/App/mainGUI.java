//mainGUI.java
//This is the main class that will run the program / start the GUI

//Austin Stephens
//Rasmussen University
//CEN4071C
//Professor Zayaz
//09/04/2024

package App;

import javax.swing.*;



public class mainGUI {
    public static void main(String[] args) {

        //we start the main frame of the application
        SwingUtilities.invokeLater(() -> {
            //create the home frame and set it to visible
            homeFrame home = new homeFrame();
            home.setVisible(true);
        });
    }
}