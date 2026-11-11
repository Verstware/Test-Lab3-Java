package com.shop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {
    @Test
    void testAddItemSuccess() {
        Product product = new Product("prod_1", "Смартфон", 50000.0, 10);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(product, 2);
        assertEquals(2, cart.getItems().get("prod_1").getQuantity());
    }

    @Test
    void testAddItemInvalidQuantity() {
        Product product = new Product("prod_1", "Смартфон", 50000.0, 10);
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(product, 0));
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(product, -1));
    }

    @Test
    void testAddItemExceedStock() {
        Product product = new Product("prod_1", "Смартфон", 50000.0, 5);
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(product, 6));
    }

    @Test
    void testRemoveItem() {
        Product product = new Product("prod_1", "Смартфон", 50000.0, 10);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(product, 5);
        cart.removeItem("prod_1", 2);
        assertEquals(3, cart.getItems().get("prod_1").getQuantity());
        cart.removeItem("prod_1");
        assertFalse(cart.getItems().containsKey("prod_1"));
    }

    @Test
    void testTotalPriceAndPromo() {
        Product product1 = new Product("prod_1", "Товар 1", 100.0, 10);
        Product product2 = new Product("prod_2", "Товар 2", 200.0, 10);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(product1, 2);
        cart.addItem(product2, 1);
        assertEquals(400.0, cart.getTotalPrice(), 0.001);
        cart.applyPromo("DISCOUNT10");
        assertEquals(360.0, cart.getTotalPrice(), 0.001);
        assertThrows(IllegalArgumentException.class, () -> cart.applyPromo("WRONGCODE"));
    }
}
