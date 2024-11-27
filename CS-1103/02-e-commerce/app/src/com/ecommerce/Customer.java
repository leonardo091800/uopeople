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

  // Methods to manage shopping cart
  public void addProductToCart(Product product) {
    shoppingCart.add(product);
  }

  public void removeProductFromCart(Product product) {
    shoppingCart.remove(product);
  }

  public double calculateTotalCost() {
    return shoppingCart.stream().mapToDouble(Product::getPrice).sum();
  }

  public void placeOrder() {
    // Placeholder for placing an order logic
    // for each product check that number exist in inventory,

    // if so decrease its number in inventory and say order success
    System.out.println("Order placed successfully.");

    // for each product check that number exist in inventory, if so decrease its
    // number in inventory and say order success
    System.out.println("Order unsuccessful, number of Item X is more than what in inventory.");
  }
}
