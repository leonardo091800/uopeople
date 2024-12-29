package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
  private static final String SERVER_HOST = "localhost";
  private static final int SERVER_PORT = 12345;

  public static void main(String[] args) {
    try (
        Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in)) {
      System.out.println("Connected to chat server.");

      Thread readerThread = new Thread(() -> {
        try {
          String message;
          while ((message = in.readLine()) != null) {
            System.out.println(message);
          }
        } catch (IOException e) {
          System.err.println("Error reading from server: " + e.getMessage());
        }
      });

      readerThread.start();

      while (true) {
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
          break;
        }
        out.println(input);
      }
    } catch (IOException e) {
      System.err.println("Error connecting to server: " + e.getMessage());
    }
  }
}
