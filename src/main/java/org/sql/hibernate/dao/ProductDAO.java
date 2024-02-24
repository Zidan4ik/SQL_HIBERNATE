package org.sql.hibernate.dao;

import org.sql.hibernate.entity.Product;

import java.util.List;

public interface ProductDAO {
    void create(Product product);
    List<Product> getAll();
    Product getById(int id);
    void update(Product product, int id);
    void delete(int id);
}
