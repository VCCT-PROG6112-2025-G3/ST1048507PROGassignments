package tutorial.onlinestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 ==========================Tests for the Online Store Inventory=================================
 */
public class OnlinestoreTest {

    @Before
    public void setUp() {
        Onlinestore.PRODUCT_DB.clear();
    }

    @After
    public void tearDown() {
        Onlinestore.PRODUCT_DB.clear();
    }

    @Test
    public void testAddProducts() {
        Book b = new Book(101, "Java Basics", "James Gosling");
        Laptop l = new Laptop(102, "Dell XPS 13", 16);
        Smartphone s = new Smartphone(103, "Pixel 9", 50);

        Onlinestore.PRODUCT_DB.add(b);
        Onlinestore.PRODUCT_DB.add(l);
        Onlinestore.PRODUCT_DB.add(s);

        assertEquals(3, Onlinestore.PRODUCT_DB.size());
        assertEquals("Java Basics", Onlinestore.PRODUCT_DB.get(0).getName());
        assertTrue(Onlinestore.PRODUCT_DB.get(1) instanceof Laptop);
        assertTrue(Onlinestore.PRODUCT_DB.get(2) instanceof Smartphone);
    }

    @Test
    public void testFindById() {
        Onlinestore.PRODUCT_DB.add(new Book(201, "OOP in Java", "Oracle"));
        Onlinestore.PRODUCT_DB.add(new Laptop(202, "MacBook Air", 8));

        Product p1 = Onlinestore.findById(201);
        Product p2 = Onlinestore.findById(202);
        Product p3 = Onlinestore.findById(999); // not present

        assertNotNull(p1);
        assertEquals("OOP in Java", p1.getName());
        assertTrue(p2 instanceof Laptop);
        assertNull(p3);
    }

    @Test
    public void testUpdateName() {
        Smartphone s = new Smartphone(301, "iPhone 15", 48);
        Onlinestore.PRODUCT_DB.add(s);

        Product target = Onlinestore.findById(301);
        assertNotNull(target);

        target.setName("iPhone 16 Pro");
        assertEquals("iPhone 16 Pro", Onlinestore.findById(301).getName());
    }

    @Test
    public void testDeleteById() {
        Onlinestore.PRODUCT_DB.add(new Book(401, "Algorithms", "CLRS"));
        Onlinestore.PRODUCT_DB.add(new Laptop(402, "ThinkPad X1", 32));

        // delete ID 401 using removeIf
        boolean removed = Onlinestore.PRODUCT_DB.removeIf(p -> p.getId() == 401);
        assertTrue(removed);

        assertNull(Onlinestore.findById(401));
        assertNotNull(Onlinestore.findById(402));
        assertEquals(1, Onlinestore.PRODUCT_DB.size());
    }

    @Test
    public void testPolymorphicShowDetailsDoesNotCrash() {
        Onlinestore.PRODUCT_DB.add(new Book(501, "Clean Code", "Robert C. Martin"));
        Onlinestore.PRODUCT_DB.add(new Laptop(502, "HP Envy", 16));
        Onlinestore.PRODUCT_DB.add(new Smartphone(503, "Galaxy S24", 200));

        // We just ensure calling the overridden method on each type doesn't throw.
        for (Product p : Onlinestore.PRODUCT_DB) {
            p.showDetails(); // output to console not captured; test is "no exception thrown"
        }
        assertEquals(3, Onlinestore.PRODUCT_DB.size());
    }
}