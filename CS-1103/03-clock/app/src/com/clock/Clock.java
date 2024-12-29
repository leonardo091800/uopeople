import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock {

    private volatile String currentTime; // Shared resource for storing the current time

    public Clock() {
        // Initialize with the current time
        updateCurrentTime();
    }

    // Method to continuously update the current time
    public void updateCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        currentTime = formatter.format(new Date());
    }

    // Getter for the current time
    public String getCurrentTime() {
        return currentTime;
    }

    public static void main(String[] args) {
        Clock clock = new Clock();

        // Thread to update the time in the background
        Thread timeUpdaterThread = new Thread(() -> {
            while (true) {
                clock.updateCurrentTime();
                try {
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread to print the time to the console
        Thread timePrinterThread = new Thread(() -> {
            while (true) {
                System.out.println(clock.getCurrentTime());
                try {
                    Thread.sleep(1000); // Print every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Set thread priorities
        timeUpdaterThread.setPriority(Thread.MIN_PRIORITY); // Lower priority for updating
        timePrinterThread.setPriority(Thread.MAX_PRIORITY); // Higher priority for displaying

        // Start both threads
        timeUpdaterThread.start();
        timePrinterThread.start();
    }
}

