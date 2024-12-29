package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
  private static final int PORT = 12345;
  private static Map<String, PrintWriter> clients = new HashMap<>();

  public static void main(String[] args) {
    System.out.println("Chat server started on port " + PORT);

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (true) {
        Socket clientSocket = serverSocket.accept();
        new ClientHandler(clientSocket).start();
      }
    } catch (IOException e) {
      System.err.println("Error starting server: " + e.getMessage());
    }
  }

  private static class ClientHandler extends Thread {
    private Socket socket;
    private String userId;

    public ClientHandler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      try (
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
        userId = "User" + (clients.size() + 1);
        synchronized (clients) {
          clients.put(userId, out);
        }
        broadcastMessage(userId + " has joined the chat.");

        String message;
        while ((message = in.readLine()) != null) {
          broadcastMessage(userId + ": " + message);
        }
      } catch (IOException e) {
        System.err.println("Connection error with " + userId);
      } finally {
        synchronized (clients) {
          clients.remove(userId);
        }
        broadcastMessage(userId + " has left the chat.");
        try {
          socket.close();
        } catch (IOException e) {
          System.err.println("Error closing socket for " + userId);
        }
      }
    }

    private void broadcastMessage(String message) {
      synchronized (clients) {
        for (PrintWriter writer : clients.values()) {
          writer.println(message);
        }
      }
    }
  }
}
