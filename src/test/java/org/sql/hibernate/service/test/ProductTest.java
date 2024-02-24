package org.sql.hibernate.service.test;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.sql.hibernate.connect.HibernateUtil;
import org.sql.hibernate.entity.Product;
import org.sql.hibernate.service.ProductService;

public class ProductTest {
    @Test
    public void create(){
        ProductService productService = new ProductService();
        Product product = new Product("product",100);
        productService.create(product);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        query.select(root).where(cb.equal(root.get("id"),product.getId()));

        Product productFromDB = session.createQuery(query).getSingleResult();

        Assert.assertEquals(productFromDB.getId(),product.getId());
        Assert.assertEquals(productFromDB.getName(),product.getName());
        Assert.assertEquals(productFromDB.getPrice(),product.getPrice());

    }

    @Test
    public void getById(){
        int productId = 1;
        ProductService productService = new ProductService();
        Product productFromMethod = productService.getById(productId);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        query.select(root).where(cb.equal(root.get("id"),productId));

        Product productFromCB = session.createQuery(query).getSingleResult();

        Assert.assertEquals(productFromCB.getId(),productFromMethod.getId());
    }

    @Test
    public void update(){
        int productId = 1;
        ProductService productService = new ProductService();
        Product product = new Product("lemon",200);
        productService.update(product,productId);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Product productFromSession = session.get(Product.class, productId);

        Assert.assertEquals(product.getId(),productFromSession.getId());
        Assert.assertEquals(product.getName(),productFromSession.getName());
        Assert.assertEquals(product.getPrice(),productFromSession.getPrice());
    }
    @Test
    public void delete(){
        int productId = 2;
        ProductService productService = new ProductService();
        productService.delete(productId);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Product productFromSession = session.get(Product.class, productId);

        Assert.assertNull(productFromSession);
    }
}
