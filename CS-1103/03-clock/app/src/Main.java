import com.clock.Clock;

public class Main {
  public static void main(String[] args) {
    // Create an instance of the Clock class
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
