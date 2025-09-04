package tutorial.onlinestore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/*
  Scenario:
  An online store wants to manage its product inventory. 
  Products belong to categories: Book, Laptop, Smartphone.
  The system must allow adding, searching, updating, deleting products, 
 and printing a full stock report.
 */

/*
References:
 * - W3Schools (Java Inheritance): https://www.w3schools.com/java/java_inheritance.asp
 * - GeeksforGeeks (ArrayList in Java): https://www.geeksforgeeks.org/arraylist-in-java/
 * - W3Schools (Java Polymorphism): https://www.w3schools.com/java/java_polymorphism.asp
 * - General Java syntax and loops: GeeksforGeeks
*/
public class Onlinestore {
    
public static final ArrayList<Product> PRODUCT_DB = new ArrayList<>();
    public static final Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        System.out.println("ONLINE STORE INVENTORY - 2025");
        System.out.println("********************************");
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        String launch = input.nextLine();

        if (!"1".equals(launch)) {
            System.out.println("Goodbye!");
            return;
        }

        boolean running = true;
        while (running) {
            printMenu();
            String choice = input.nextLine();

            switch (choice) {
                case "1" -> addProduct();
                case "2" -> searchProduct();
                case "3" -> updateProduct();
                case "4" -> deleteProduct();
                case "5" -> printReport();
                case "6" -> {
                    System.out.println("Exiting application...");
                    running = false;
                }
                default -> System.out.println("Invalid option! Please choose 1-6.");
            }

            if (running) {
                System.out.print("\nEnter (1) to return to menu or any other key to exit: ");
                String again = input.nextLine();
                if (!"1".equals(again)) {
                    System.out.println("Goodbye!");
                    break;
                }
            }
        }
    }

    // Menu
       public  static void printMenu() {
        System.out.println("\nPlease select one of the following menu items:");
        System.out.println("(1) Add a new product");
        System.out.println("(2) Search for a product");
        System.out.println("(3) Update product details");
        System.out.println("(4) Delete a product");
        System.out.println("(5) Print inventory report");
        System.out.println("(6) Exit application");
        System.out.print("Your choice: ");
    }

    // 1. Add Product
      public  static void addProduct() {
        System.out.println("\nADD A NEW PRODUCT");
        System.out.println("********************************");

        System.out.print("Enter Product ID: ");
        int id = Integer.parseInt(input.nextLine());

        System.out.print("Enter Product Name: ");
        String name = input.nextLine();

        System.out.println("Select Product Type: (1) Book, (2) Laptop, (3) Smartphone");
        String type = input.nextLine();

        Product newProduct = null;
        switch (type) {
            case "1" -> {
                System.out.print("Enter Author Name: ");
                String author = input.nextLine();
                newProduct = new Book(id, name, author);
            }
            case "2" -> {
                System.out.print("Enter RAM size (GB): ");
                int ram = Integer.parseInt(input.nextLine());
                newProduct = new Laptop(id, name, ram);
            }
            case "3" -> {
                System.out.print("Enter Camera (MP): ");
                int cam = Integer.parseInt(input.nextLine());
                newProduct = new Smartphone(id, name, cam);
            }
            default -> {
                System.out.println("Invalid type! Product not added.");
                return;
            }
        }

        PRODUCT_DB.add(newProduct);
        System.out.println("Product added successfully!");
    }

    // 2. Search Product
      public  static void searchProduct() {
        System.out.print("\nEnter Product ID to search: ");
        int id = Integer.parseInt(input.nextLine());
        Product p = findById(id);

        if (p == null) {
            System.out.println("Product with ID " + id + " was not found!");
        } else {
            System.out.println("Product found:");
            p.showDetails(); // Polymorphism in action
        }
    }

    // 3. Update Product
      public  static void updateProduct() {
        System.out.print("\nEnter Product ID to update: ");
        int id = Integer.parseInt(input.nextLine());
        Product p = findById(id);

        if (p == null) {
            System.out.println("Product with ID " + id + " was not found!");
            return;
        }

        System.out.print("Enter new Product Name: ");
        String newName = input.nextLine();
        p.setName(newName);

        if (p instanceof Book b) {
            System.out.print("Enter new Author: ");
            b.setAuthor(input.nextLine());
        } else if (p instanceof Laptop l) {
            System.out.print("Enter new RAM size: ");
            l.setRam(Integer.parseInt(input.nextLine()));
        } else if (p instanceof Smartphone s) {
            System.out.print("Enter new Camera MP: ");
            s.setCamera(Integer.parseInt(input.nextLine()));
        }

        System.out.println("Update completed!");
    }

    // 4. Delete Product
      public  static void deleteProduct() {
        System.out.print("\nEnter Product ID to delete: ");
        int id = Integer.parseInt(input.nextLine());

        Iterator<Product> it = PRODUCT_DB.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                System.out.println("Product with ID " + id + " was deleted!");
                return;
            }
        }
        System.out.println("Product with ID " + id + " not found!");
    }

    // 5. Print Report
       public  static void printReport() {
        if (PRODUCT_DB.isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }
        System.out.println("\nINVENTORY REPORT");
        System.out.println("********************************");
        for (int i = 0; i < PRODUCT_DB.size(); i++) {
            System.out.println("Product " + (i + 1));
            PRODUCT_DB.get(i).showDetails(); // Polymorphism
            System.out.println("--------------------------------");
        }
    }

    // Helper: Find product by ID
   public static Product findById(int id) {
        for (Product p : PRODUCT_DB) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public static void addProductDirect(java.awt.print.Book b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public ArrayList<Object> products;
}

/* ===============================
   Base Class + Subclasses
   =============================== */

abstract class Product {
       public int id;
       public  String name;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public abstract void showDetails(); // Overridden by subclasses
}

class Book extends Product {
       public String author;

    public Book(int id, String name, String author) {
        super(id, name);
        this.author = author;
    }

    public void setAuthor(String author) { this.author = author; }

    @Override
    public void showDetails() {
        System.out.println("ID: " + getId());
        System.out.println("Book Name: " + getName());
        System.out.println("Author: " + author);
    }
}

class Laptop extends Product {
   public  int ram;

    public Laptop(int id, String name, int ram) {
        super(id, name);
        this.ram = ram;
    }

    public void setRam(int ram) { this.ram = ram; }

    @Override
    public void showDetails() {
        System.out.println("ID: " + getId());
        System.out.println("Laptop Name: " + getName());
        System.out.println("RAM: " + ram + " GB");
    }
}

class Smartphone extends Product {
    public int camera;

    public Smartphone(int id, String name, int camera) {
        super(id, name);
        this.camera = camera;
    }

    public void setCamera(int camera) { this.camera = camera; }

    @Override
    public void showDetails() {
        System.out.println("ID: " + getId());
        System.out.println("Smartphone Name: " + getName());
        System.out.println("Camera: " + camera + " MP");
    }

    }

