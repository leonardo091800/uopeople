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

  // Map to store color names and corresponding ANSI codes
  private static final Map<String, String> colorMap = new HashMap<>();
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
    Scanner scanner = new Scanner(System.in);

    // Prompt the user to choose a color before connecting to the chat.
    System.out.println("Welcome to the chat! Please choose your color:");
    displayColorOptions();

    int colorChoice = getUserColorChoice(scanner);
    clientColor = getColorFromChoice(colorChoice);

    try (
        Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
      System.out.println("Connected to chat server.");

      // Thread to handle incoming messages from the server.
      Thread readerThread = new Thread(() -> {
        try {
          String message;
          while ((message = in.readLine()) != null) {
            // Check if the message is from the server.
            if (message.startsWith(SERVER_COLOR)) {
              System.out.println(message + RESET_COLOR);
            } else {
              // Display the message as received.
              System.out.println(message);
            }
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
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
          socket.close(); // Close the socket to exit the chat.
          break;
        }
        // Prepend the client color to the message before sending.
        out.println(clientColor + input + RESET_COLOR); // Send the message with client color.
      }
    } catch (IOException e) {
      System.err.println("Error connecting to server: " + e.getMessage());
    }
  }

  /**
   * Displays the list of available color options with their corresponding colors.
   */
  private void displayColorOptions() {
    System.out.println("Available Colors:");
    int index = 1;
    for (Map.Entry<String, String> entry : colorMap.entrySet()) {
      System.out.println(index + ". " + entry.getValue() + entry.getKey() + RESET_COLOR);
      index++;
    }
    System.out.print("Enter the number corresponding to your color choice: ");
  }

  /**
   * Gets the color choice from the user.
   *
   * @param scanner The scanner to read input from the user.
   * @return The color choice entered by the user.
   */
  private int getUserColorChoice(Scanner scanner) {
    int choice;
    while (true) {
      try {
        choice = Integer.parseInt(scanner.nextLine());
        if (choice < 1 || choice > colorMap.size()) {
          System.out.print("Invalid choice. Please choose a number between 1 and " + colorMap.size() + ": ");
        } else {
          break;
        }
      } catch (NumberFormatException e) {
        System.out.print("Invalid input. Please enter a number between 1 and " + colorMap.size() + ": ");
      }
    }
    return choice;
  }

  /**
   * Converts the user's color choice to an ANSI color code.
   *
   * @param choice The color choice (1-13).
   * @return The corresponding ANSI color code as a string.
   */
  private String getColorFromChoice(int choice) {
    // Get the color code from the HashMap using its entry number (adjust for
    // 1-based index)
    return (String) colorMap.values().toArray()[choice - 1];
  }
}

