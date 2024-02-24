package org.sql.hibernate.service.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.sql.hibernate.connect.HibernateUtil;
import org.sql.hibernate.entity.Product;
import org.sql.hibernate.entity.User;
import org.sql.hibernate.service.ShoppingCartService;


import java.util.List;

public class ShoppingCartTest {
    @Test
    public void addProductByUser() {
        int userId = 1;
        int productId = 1;
        int count1 = 0;
        int count2 = 0;
        ShoppingCartService shoppingCartService = new ShoppingCartService();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        List<Product> productsBefore = shoppingCartService.getAllProductsFromUser(userId);
        for (Product i : productsBefore) {
            if (i.getId() == productId) count1++;
        }

        shoppingCartService.addProductToUser(userId, productId);

        List<Product> productsAfter = shoppingCartService.getAllProductsFromUser(userId);
        for (Product i : productsAfter) {
            if (i.getId() == productId) count2++;
        }

        Assert.assertTrue("Кількість продуктів користувача у кошику не змінилась",
                count1 != count2);
        System.out.println(count1);
        System.out.println(count2);

        session.close();

    }

    @Test
    public void removeProductFromUser() throws Exception {
        int userId = 1;
        int productId = 1;
        int count1 = 0;
        int count2 = 0;
        ShoppingCartService shoppingCartService = new ShoppingCartService();

        List<Product> productsBefore = shoppingCartService.getAllProductsFromUser(userId);
        shoppingCartService.removeProductFromUser(userId, productId);
        List<Product> productsAfter = shoppingCartService.getAllProductsFromUser(userId);

        for (Product p : productsBefore) {
            if (p.getId() == productId) count1++;
        }
        for (Product p : productsAfter) {
            if (p.getId() == productId) count2++;
        }

        if(productsBefore.size()!=0){
            Assert.assertTrue("Кількість продуктів не змінилась", productsBefore.size() > productsAfter.size());
            Assert.assertTrue("Видалився не відповідний продукт", count1 > count2);
        }else{
            throw new Exception("В даного користувача немає продуктів в кошику");
        }
    }

    @Test
    public void getAllProductsByUser() {
        int userId = 1;
        ShoppingCartService shoppingCartService = new ShoppingCartService();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List productsFromDB = session.createNativeQuery("SELECT * FROM shopping_carts WHERE user_id=" + userId).getResultList();
        List productsFromMethod = shoppingCartService.getAllProductsFromUser(userId);

        Assert.assertEquals(productsFromDB.size(), productsFromMethod.size());
    }

    @Test
    public void removeAllProductFromBD() throws Exception {
        int userId = 2;
        ShoppingCartService shoppingCartService = new ShoppingCartService();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List productsFromDBBefore = session.createNativeQuery("SELECT * FROM shopping_carts WHERE user_id=" + userId).getResultList();
        session.close();
        shoppingCartService.removeAllProductsFromUser(userId);
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        List productsFromDBAfter = session.createNativeQuery("SELECT * FROM shopping_carts WHERE user_id=" + userId).getResultList();


        if (productsFromDBBefore.size()==0) {
            throw new Exception("В даного користувача немає продуктів в кошику");
        } else {
            Assert.assertNotEquals(productsFromDBBefore.size(), productsFromDBAfter.size());
            Assert.assertEquals(productsFromDBAfter.size(), 0);
        }
    }
}
