package org.sql.hibernate.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    private int id;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Product> products;

    public ShoppingCart() {
    }

    public ShoppingCart(int id, User user, List<Product> products) {
        this.id = id;
        this.user = user;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}
