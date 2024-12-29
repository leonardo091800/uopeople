package library;

/**
 * Represents a library item with a title, author, and item ID.
 */
public class LibraryItem {

  private String title;
  private String author;
  private String itemID;

  /**
   * Constructs a new LibraryItem object.
   * 
   * @param title  The title of the item.
   * @param author The author of the item.
   * @param itemID The unique ID for the item.
   */
  public LibraryItem(String title, String author, String itemID) {
    this.title = title;
    this.author = author;
    this.itemID = itemID;
  }

  /**
   * Gets the title of the item.
   * 
   * @return The title of the item.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the author of the item.
   * 
   * @return The author of the item.
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Gets the unique ID of the item.
   * 
   * @return The ID of the item.
   */
  public String getItemID() {
    return itemID;
  }

  /**
   * Returns a string representation of the LibraryItem.
   * 
   * @return A string that describes the LibraryItem.
   */
  @Override
  public String toString() {
    return "LibraryItem{" +
        "title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", itemID='" + itemID + '\'' +
        '}';
  }
}
