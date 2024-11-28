package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

/**
 * This class should represent a customer with attributes like
 * - customerID
 * - name
 * - shopping cart
 * Implement methods
 * - to add
 * - to remove products from the shopping cart
 * - calculate the total cost
 * - place orders. 
 */
public class Customer {
  private int customerID;
  private String name;
  private List<Product> shoppingCart;

  public Customer(int customerID, String name) {
    this.customerID = customerID;
    this.name = name;
    this.shoppingCart = new ArrayList<>();
  }

  // Getters and setters
  public int getCustomerID() {
    return customerID;
  }

  public String getName() {
    return name;
  }

  public List<Product> getShoppingCart() {
    return shoppingCart;
  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Customer{" + "customerID=" + customerID + ", name='" + name + '\'' + ", shoppingCart=" + shoppingCart + '}';
  }

  // Add product to shopping cart if inventory allows
  public boolean addProductToCart(Product product) {
    if (product.decreaseInventory(1)) {
      shoppingCart.add(product);
      return true;
    } else {
      System.out.println("Product " + product.getName() + " is out of stock.");
      return false;
    }
  }

  // Remove product from cart and restore inventory
  public void removeProductFromCart(Product product) {
    if (shoppingCart.remove(product)) {
      product.setInventoryCount(product.getInventoryCount() + 1);
    }
  }

  public double calculateTotalCost() {
    return shoppingCart.stream().mapToDouble(Product::getPrice).sum();
  }

  public void placeOrder() {
    if (!shoppingCart.isEmpty()) {
      System.out.println("Order placed successfully.");
      shoppingCart.clear();
    } else {
      System.out.println("Shopping cart is empty.");
    }
  }
}
