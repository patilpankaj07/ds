import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class TokenMutualExclusion {

    public static int MAX_PROCESS = 4;
    public static Semaphore[] semaphores = new Semaphore[MAX_PROCESS];
    public static int SLEEP_TIME = 5000;
    public static int tokenHolder = 0;

    public static void main(String[] args) {
        for (int i = 0; i < MAX_PROCESS; i++) {
            semaphores[i] = new Semaphore(1);
        }

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < MAX_PROCESS; i++) {
            final int processId = i;
            Thread t = new Thread(() -> runProcess(processId));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static void runProcess(int processId) {
        try {
            while (true) {
                if (processId == tokenHolder) {
                    semaphores[processId].acquire();
                    System.out.println("Process " + processId + " enters critical section");
                    Thread.sleep(SLEEP_TIME);
                    System.out.println("Process " + processId + " leaves critical section");
                    passToken(processId);
                    semaphores[processId].release();
                } else {
                    System.out.println("Process id " + processId + " is waiting for token...");
                    Thread.sleep(SLEEP_TIME);
                }

            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void passToken(int processId) {
        tokenHolder = (processId + 1) % MAX_PROCESS; // ring based token passing
        System.out.println("Process " + processId + " passed token to " + tokenHolder);
    }
}
