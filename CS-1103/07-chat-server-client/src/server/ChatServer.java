package server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A multi-threaded chat server that allows clients to connect and exchange
 * messages.
 * The server forwards messages as-is without modifying them.
 */
public class ChatServer {
  private static final int PORT = 12345; // Port number for the server to listen on.
  private static Map<String, PrintWriter> clients = new HashMap<>(); // Stores active clients and their output streams.
  private static final String SERVER_COLOR = "\u001B[37m"; // White color for server messages.
  private static final String RESET_COLOR = "\u001B[0m"; // Reset color.

  public static void main(String[] args) {
    System.out.println("Chat server started on port " + PORT);

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      // Continuously accept new client connections.
      while (true) {
        Socket clientSocket = serverSocket.accept();
        new ClientHandler(clientSocket).start(); // Start a new thread for each client.
      }
    } catch (IOException e) {
      System.err.println("Error starting server: " + e.getMessage());
    }
  }

  /**
   * Handles communication with a single client.
   */
  private static class ClientHandler extends Thread {
    private Socket socket; // Socket for client communication.
    private String userId; // Unique ID assigned to the client.

    public ClientHandler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      try (
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
        // Assign a unique user ID to the client.
        userId = "User" + (clients.size() + 1);
        synchronized (clients) {
          clients.put(userId, out);
        }
        broadcastMessage(SERVER_COLOR + userId + " has joined the chat." + RESET_COLOR, null);

        // Read messages from the client and broadcast them.
        String message;
        while ((message = in.readLine()) != null) {
          // Prepend the userId before broadcasting the message (keep client color intact)
          // Regular expression to match ANSI color codes
          String colorCodeRegex = "^(\u001B\\[[0-9;]*m)";
          String colorCode = "";

          // Extract the color code if it exists
          if (message.matches(colorCodeRegex + ".*")) {
            colorCode = message.replaceFirst(colorCodeRegex + ".*", "$1");
            message = message.replaceFirst(colorCodeRegex, ""); // Remove the color code
          }

          // Add "UserID: " at the beginning and reattach the color code
          String fullMessage = colorCode + userId + ": " + message;
          broadcastMessage(fullMessage, out);
        }
      } catch (IOException e) {
        System.err.println("Connection error with " + userId);
      } finally {
        // Remove the client when they disconnect.
        synchronized (clients) {
          clients.remove(userId);
        }
        broadcastMessage(SERVER_COLOR + userId + " has left the chat." + RESET_COLOR, null);
        try {
          socket.close();
        } catch (IOException e) {
          System.err.println("Error closing socket for " + userId);
        }
      }
    }

    /**
     * Broadcasts a message to all connected clients except the sender.
     */
    private void broadcastMessage(String message, PrintWriter excludeWriter) {
      synchronized (clients) {
        for (PrintWriter writer : clients.values()) {
          if (writer != excludeWriter) {
            writer.println(message); // Send the message to all other clients.
          }
        }
      }
    }
  }
}
