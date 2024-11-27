import com.ecommerce.Product;
import com.ecommerce.Customer;
import com.ecommerce.orders.Order;

import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    // 1. Create instances of products
    Product product1 = new Product(101, "Laptop", 1500.00, 10);
    Product product2 = new Product(102, "Smartphone", 800.00, 20);
    Product product3 = new Product(103, "Headphones", 200.00, 15);

    // Display available products
    System.out.println("Available Products:");
    System.out.println(product1);
    System.out.println(product2);
    System.out.println(product3);

    // 2. Create a customer
    Customer customer1 = new Customer(201, "Alice");
    System.out.println("\nCustomer Created: " + customer1);

    // 3. Customer browses products and adds to shopping cart
    System.out.println("\nBrowsing and adding products to cart...");
    if (customer1.addProductToCart(product1)) {
      System.out.println("Added: " + product1.getName());
    }
    if (customer1.addProductToCart(product2)) {
      System.out.println("Added: " + product2.getName());
    }
    if (customer1.addProductToCart(product3)) {
      System.out.println("Added: " + product3.getName());
    }

    // Display shopping cart
    System.out.println("\nShopping Cart:");
    customer1.getShoppingCart().forEach(System.out::println);

    // 4. Customer places an order
    System.out.println("\nPlacing an order...");
    List<Product> orderedProducts = customer1.getShoppingCart();
    Order order1 = new Order(301, customer1, orderedProducts);

    // Clear customer's cart after placing order
    customer1.getShoppingCart().clear();

    // 5. Display order details
    System.out.println("\nOrder Details:");
    order1.generateOrderSummary();

    // 6. Display updated product inventory
    System.out.println("\nUpdated Inventory:");
    System.out.println(product1);
    System.out.println(product2);
    System.out.println(product3);
  }
}
