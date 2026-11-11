package com.shop;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    public static class CartItem {
        private Product product;
        private int quantity;

        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    private Map<String, CartItem> items = new HashMap<>();
    private double discount = 0.0;

    public void addItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть больше нуля");
        }
        CartItem existingItem = items.get(product.getId());
        int currentInCart = existingItem != null ? existingItem.getQuantity() : 0;
        int totalRequested = currentInCart + quantity;

        if (totalRequested > product.getStock()) {
            throw new IllegalArgumentException("Превышено доступное количество на складе");
        }
        if (existingItem != null) {
            existingItem.setQuantity(totalRequested);
        } else {
            items.put(product.getId(), new CartItem(product, quantity));
        }
    }

    public void removeItem(String productId) {
        removeItem(productId, null);
    }

    public void removeItem(String productId, Integer quantity) {
        if (!items.containsKey(productId)) {
            return;
        }
        CartItem item = items.get(productId);
        if (quantity == null || quantity >= item.getQuantity()) {
            items.remove(productId);
        } else {
            item.setQuantity(item.getQuantity() - quantity);
        }
    }

    public void applyPromo(String code) {
        if ("DISCOUNT10".equals(code)) {
            this.discount = 0.10;
        } else if ("DISCOUNT20".equals(code)) {
            this.discount = 0.20;
        } else {
            throw new IllegalArgumentException("Неверный промокод");
        }
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (CartItem item : items.values()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total * (1.0 - this.discount);
    }

    public Map<String, CartItem> getItems() {
        return items;
    }
}
