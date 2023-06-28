import java.util.Scanner;

public class TimerStopwatch {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Timer/Stopwatch program!");
        System.out.println("Enter '1' for Timer or '2' for Stopwatch:");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                runTimer();
                break;
            case 2:
                runStopwatch();
                break;
            default:
                System.out.println("Invalid choice. Exiting the program.");
        }
        sc.close();
    }

    private static void runTimer() {
        System.out.println("Enter the duration in seconds:");
        int duration = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        System.out.println("Timer started!");
        for (int i = duration; i > 0; i--) {
            System.out.println("Remaining time: " + i + " seconds");
            sleepSeconds(1);
        }

        System.out.println("Time's up!");
    }

    private static void runStopwatch() {
        System.out.println("Stopwatch started!");
        System.out.println("Press Enter to stop the stopwatch.");

        long startTime = System.currentTimeMillis();
        sc.nextLine(); // Wait for Enter key press
        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time: " + elapsedTime / 1000 + " seconds");
    }

    private static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
