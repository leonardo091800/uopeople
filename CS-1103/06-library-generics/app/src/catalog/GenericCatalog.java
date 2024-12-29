package catalog;

import library.LibraryItem;
import java.util.*;

/**
 * A generic catalog for managing library items.
 * 
 * @param <T> A type that extends LibraryItem.
 */
public class GenericCatalog<T extends LibraryItem> {

  private Map<String, T> catalog;

  /**
   * Constructs a new GenericCatalog object.
   * Initializes an empty catalog (HashMap).
   */
  public GenericCatalog() {
    this.catalog = new HashMap<>();
  }

  /**
   * Adds an item to the catalog.
   * If an item with the same ID already exists, a message is printed.
   * 
   * @param item The library item to add.
   */
  public void addItem(T item) {
    if (catalog.containsKey(item.getItemID())) {
      System.out.println("Item with ID " + item.getItemID() + " already exists.");
    } else {
      catalog.put(item.getItemID(), item);
      System.out.println("Item added successfully.");
    }
  }

  /**
   * Removes an item from the catalog by its ID.
   * If the item does not exist, a message is printed.
   * 
   * @param itemID The ID of the item to remove.
   */
  public void removeItem(String itemID) {
    if (catalog.containsKey(itemID)) {
      catalog.remove(itemID);
      System.out.println("Item removed successfully.");
    } else {
      System.out.println("Item with ID " + itemID + " does not exist.");
    }
  }

  /**
   * Retrieves an item from the catalog by its ID.
   * 
   * @param itemID The ID of the item to retrieve.
   * @return The library item associated with the given ID, or null if not found.
   */
  public T getItem(String itemID) {
    return catalog.get(itemID);
  }

  /**
   * Displays all the items in the catalog.
   * If the catalog is empty, a message is printed.
   */
  public void displayCatalog() {
    if (catalog.isEmpty()) {
      System.out.println("The catalog is empty.");
    } else {
      catalog.values().forEach(item -> System.out.println(item.toString()));
    }
  }
}
