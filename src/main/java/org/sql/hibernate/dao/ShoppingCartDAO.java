package org.sql.hibernate.dao;

import org.sql.hibernate.entity.Product;

import java.util.List;

public interface ShoppingCartDAO {
    void addProductToUser(int userId,int productId);
    void removeProductFromUser(int userId,int productId);
    List<Product> getAllProductsFromUser(int userId);
    void removeAllProductsFromUser(int userId);
}
