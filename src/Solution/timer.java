//timer.java
//This class will be used to calculate the time it takes to encrypt a large text file using AES, Blowfish, and RC4.
//We will have a testing section to the GUI to display the results.

//Austin Stephens
//Rasmussen University
//CEN4071C
//Professor Zayaz
//09/04/2024


package Solution;

import java.util.ArrayList;
import java.util.List;

public class timer {
    private long startTime;
    private long endTime;
    private List<Long> averageTimes = new ArrayList<>();
    private List<String> algorithms = new ArrayList<>();

    // This method will start the timer
    public void start() {
        startTime = System.currentTimeMillis();
    }

    // This method will stop the timer
    public void stop() {
        endTime = System.currentTimeMillis();
    }

    // This method will return the duration of the timer
    public long getDuration() {
        return endTime - startTime;
    }

    // This method will add an average time to the list
    public void addAverageTime(long time, String algorithm) {
        averageTimes.add(time);
        algorithms.add(algorithm);
    }

    // This method will print all average times with a summary
    public void printAverageTimes(int runs) {
        System.out.println("Encryption Test Summary:");
        System.out.println("Number of runs per algorithm: " + runs);
        System.out.println("Algorithms tested: " + String.join(", ", algorithms));
        System.out.println("\nAverage Encryption Times:");
        for (int i = 0; i < averageTimes.size(); i++) {
            System.out.println(algorithms.get(i) + ": " + averageTimes.get(i) + " ms");
        }
    }
}