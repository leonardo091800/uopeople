package client;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A chat client that connects to a chat server and exchanges messages with
 * other clients.
 * Each client broadcasts its messages with a unique color.
 */
public class ChatClient {
  private static final String SERVER_HOST = "localhost"; // Server host address.
  private static final int SERVER_PORT = 12345; // Server port number.

  private static final String RESET_COLOR = "\u001B[0m"; // ANSI reset code to reset colors.
  private static final String SERVER_COLOR = "\u001B[37m"; // White color for server messages.

  private String clientColor; // The unique color for this client.

  private static final Map<String, String> colorMap = new LinkedHashMap<>();
  static {
    colorMap.put("Red", "\u001B[31m");
    colorMap.put("Green", "\u001B[32m");
    colorMap.put("Yellow", "\u001B[33m");
    colorMap.put("Blue", "\u001B[34m");
    colorMap.put("Purple", "\u001B[35m");
    colorMap.put("Cyan", "\u001B[36m");
    colorMap.put("Bright Black", "\u001B[90m");
    colorMap.put("Bright Red", "\u001B[91m");
    colorMap.put("Bright Green", "\u001B[92m");
    colorMap.put("Bright Yellow", "\u001B[93m");
    colorMap.put("Bright Blue", "\u001B[94m");
    colorMap.put("Bright Purple", "\u001B[95m");
    colorMap.put("Bright Cyan", "\u001B[96m");
  }

  public static void main(String[] args) {
    ChatClient client = new ChatClient();
    client.start();
  }

  public void start() {
    try (
        Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in)) {
      System.out.println("Connected to chat server.");

      // Let the user select a color.
      clientColor = selectColor(scanner);

      // Thread to handle incoming messages from the server.
      Thread readerThread = new Thread(() -> {
        try {
          String message;
          while ((message = in.readLine()) != null) {
            // Move the cursor up to clear the current input line.
            System.out.print("\r");
            System.out.flush();

            // Print the received message, which will already have the color code.
            System.out.println(message);

            // Reprint the user's input prompt in their color.
            System.out.print(clientColor + "Message: ");
          }
        } catch (IOException e) {
          if (!e.getMessage().equals("Socket closed")) {
            System.err.println("Error reading from server: " + e.getMessage());
          }
        }
      });
      readerThread.start();

      // Main loop to send messages to the server.
      while (true) {
        System.out.print(clientColor + "Message: ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
          socket.close(); // Close the socket to exit the chat.
          break;
        } else if (input.equalsIgnoreCase("changecolor")) {
          clientColor = selectColor(scanner);
          System.out.println("Your color has been updated.");
        } else {
          out.println(clientColor + input + RESET_COLOR);
        }
      }
    } catch (IOException e) {
      System.err.println("Error connecting to server: " + e.getMessage());
    }
  }

  /**
   * Allows the user to select a color from a predefined list.
   *
   * @param scanner The scanner to read user input.
   * @return The ANSI color code for the selected color.
   */
  private String selectColor(Scanner scanner) {
    System.out.println("Available colors:");
    int index = 1;
    for (String colorName : colorMap.keySet()) {
      System.out.println(index + ". " + colorMap.get(colorName) + colorName + RESET_COLOR);
      index++;
    }

    while (true) {
      System.out.print("Choose a color by number: ");
      try {
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice >= 1 && choice <= colorMap.size()) {
          return (String) colorMap.values().toArray()[choice - 1];
        }
      } catch (NumberFormatException ignored) {
      }
      System.out.println("Invalid choice. Please try again.");
    }
  }
}
