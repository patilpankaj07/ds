import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started. Waiting for client...");
        Socket client = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter writer = new PrintWriter(client.getOutputStream(), true);

        int n = Integer.parseInt(reader.readLine());
        List<Integer> times = new ArrayList<>();

        for (int i = 0; i < n; i++) {
          int time = Integer.parseInt(reader.readLine());
          times.add(time);
          System.out.println("Received time for clock " + (i + 1) + ": " + time);
        }

        int sum = 0;
        for (int time : times) sum += time;

        int avg = sum / n;
        System.out.println("Average time: " + avg);

        for (int time : times) {
          int adjustment = avg - time;
          writer.println(adjustment);
          System.out.println("Sent adjustment: " + adjustment);
        }

        client.close();
        serverSocket.close();
    }
}
