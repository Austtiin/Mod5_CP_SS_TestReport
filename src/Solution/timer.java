//timer.java
//This class will be used to calculate the time it takes to encrypt a large text file using AES, Blowfish, and RC4.
//We will have a testing section to the GUI to display the results.

//Austin Stephens
//Rasmussen University
//CEN4071C
//Professor Zayaz
//09/04/2024


package Solution;


public class timer {
    private long startTime;
    private long endTime;


    //This method will start the timer
    public void start() {
        startTime = System.currentTimeMillis();
    }


    //This method will stop the timer
    public void stop() {
        endTime = System.currentTimeMillis();
    }


    //This method will return the duration of the timer
    public long getDuration() {
        return endTime - startTime;
    }
}