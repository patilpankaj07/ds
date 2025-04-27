import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of clocks: ");
        int n = scanner.nextInt();
        int[] times = new int[n];

        writer.println(n);

        for (int i = 0; i < n; i++) {
            System.out.print("Enter time for clock " + (i + 1) + ": ");
            times[i] = scanner.nextInt();
            writer.println(times[i]);
        }

        for (int i = 0; i < n; i++) {
            int adjustment = Integer.parseInt(reader.readLine());
            System.out.println("Clock " + (i + 1) + " adjustment: " + adjustment + ", New time: " + (times[i] + adjustment));
        }

        socket.close();
        scanner.close();
    }
}
