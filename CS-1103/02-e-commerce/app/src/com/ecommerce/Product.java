package com.ecommerce;

/**
 * This class should represent a product available for purchase
 * Include attributes like productID, name, price, and any other relevant
 * fields.
 * Implement the necessary constructors, getters, setters, and any other methods
 * for product-related operations.
 */
public class Product {
  private int productID;
  private String name;
  private double price;
  private int inventoryCount;

  public Product(int productID, String name, double price, int inventoryCount) {
    this.productID = productID;
    this.name = name;
    this.price = price;
    this.inventoryCount = inventoryCount;
  }

  // Implement the necessary constructors, getters, setters, and any other methods
  // for product-related operations
  public int getProductID() {
    return productID;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public int getInventoryCount() {
    return inventoryCount;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public void setInventoryCount(int inventoryCount) {
    this.inventoryCount = inventoryCount;
  }

  // Increase inventory when a product is added to an order
  public boolean increaseInventory(int quantity) {
    inventoryCount += quantity;
    return true;
  }

  // Decrease inventory when a product is added to an order
  public boolean decreaseInventory(int quantity) {
    if (inventoryCount >= quantity) {
      inventoryCount -= quantity;
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return "Product{" + "productID=" + productID + ", name='" + name + '\'' + ", price=" + price + ", inventoryCount="
        + inventoryCount + '}';
  }

}
