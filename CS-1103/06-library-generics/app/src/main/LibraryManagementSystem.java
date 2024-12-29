package main;

import catalog.GenericCatalog;
import library.LibraryItem;
import java.util.*;

/**
 * The main class for the Library Management System.
 * Provides a menu to interact with the catalog.
 */
public class LibraryManagementSystem {

  /**
   * Main entry point for the Library Management System.
   * Provides a menu-driven interface for users to manage library items.
   * 
   * @param args Command-line arguments (not used in this program).
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    GenericCatalog<LibraryItem> catalog = new GenericCatalog<>();

    // Main loop for the menu-driven interface
    while (true) {
      System.out.println("\nLibrary Management System");
      System.out.println("1. Add a new item");
      System.out.println("2. Remove an item");
      System.out.println("3. View catalog");
      System.out.println("4. Exit");
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          System.out.print("Enter title: ");
          String title = scanner.nextLine();
          System.out.print("Enter author: ");
          String author = scanner.nextLine();
          System.out.print("Enter item ID: ");
          String itemID = scanner.nextLine();
          catalog.addItem(new LibraryItem(title, author, itemID));
          break;

        case 2:
          System.out.print("Enter item ID to remove: ");
          String removeID = scanner.nextLine();
          catalog.removeItem(removeID);
          break;

        case 3:
          catalog.displayCatalog();
          break;

        case 4:
          System.out.println("Exiting the system. Goodbye!");
          scanner.close();
          return;

        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}
