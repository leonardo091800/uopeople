package com.ecommerce.orders;

import com.ecommerce.Customer;
import com.ecommerce.Product;
import java.util.List;

/**
 * This class should represent an order placed by a customer. Include attributes
 * like orderID, customer, products, and the order total. Implement methods to
 * generate order summaries, update order status, and manage order information
 */
public class Order {
  private int orderID;
  private Customer customer;
  private List<Product> products;
  private double orderTotal;

  public Order(int orderID, Customer customer, List<Product> products) {
    this.orderID = orderID;
    this.customer = customer;
    this.products = products;
    this.orderTotal = calculateOrderTotal();
  }

  // Calculate order total
  private double calculateOrderTotal() {
    return products.stream().mapToDouble(Product::getPrice).sum();
  }

  public void generateOrderSummary() {
    System.out.println("Order Summary:");
    System.out.println("Order ID: " + orderID);
    System.out.println("Customer: " + customer.getName());
    System.out.println("Products: ");
    products.forEach(product -> System.out.println(product));
    System.out.println("Order Total: $" + orderTotal);
  }

  public void updateOrderStatus(String status) {
    System.out.println("Order status updated to: " + status);
  }

  // Getters and setters
  public int getOrderID() {
    return orderID;
  }

  public void setOrderID(int orderID) {
    this.orderID = orderID;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  @Override
  public String toString() {
    return "Order{" + "orderID=" + orderID + ", customer=" + customer +
        ", products=" + products + ", orderTotal=" + orderTotal + '}';
  }
}
