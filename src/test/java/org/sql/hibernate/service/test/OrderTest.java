package org.sql.hibernate.service.test;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.sql.hibernate.connect.HibernateUtil;
import org.sql.hibernate.entity.Order;
import org.sql.hibernate.entity.Product;
import org.sql.hibernate.entity.User;
import org.sql.hibernate.service.OrderService;
import org.sql.hibernate.service.ShoppingCartService;

import java.util.List;

public class OrderTest {
    @Test
    public void create() throws Exception {
        int userId = 1;
        OrderService orderService = new OrderService();
        ShoppingCartService shoppingCartService = new ShoppingCartService();
        List<Product> productsInCart = shoppingCartService.getAllProductsFromUser(userId);
        String listInCart = "";
        String listFromDB = "";
        if (productsInCart.size() != 0) {
            for (Product product : productsInCart) {
                listInCart += product.getName() + ", ";
            }
            listInCart = listInCart.substring(0, listInCart.length() - 2);
        }

        if (shoppingCartService.getAllProductsFromUser(userId).size() == 0)
            throw new Exception("Кошику користувача пустий");

        orderService.create(userId);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        String hql = "FROM Order AS o WHERE o.user = :user";

        Query query = session.createQuery(hql);
        query.setParameter("user", session.get(User.class, userId));

        List ordersByUser = query.list();

        for (Object o : ordersByUser) {
            if (o instanceof Order) {
                if (listInCart.equals(((Order) o).getList())) {
                    listFromDB = ((Order) o).getList();
                }
            }
        }

        Assert.assertEquals(listInCart, listFromDB);
    }

    @Test
    public void getAllByUser() {
        int userId = 1;
        OrderService orderService = new OrderService();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List listFromDB = session.createNativeQuery("SELECT * FROM orders WHERE user_id=" + userId).getResultList();
        List listFromMethod = orderService.getAllByUser(userId);

        Assert.assertEquals(listFromDB.size(),listFromMethod.size());

    }

    @Test
    public void getAllOrders(){
        OrderService orderService = new OrderService();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List listFromDB = session.createNativeQuery("SELECT * FROM orders").getResultList();
        List listFromMethod = orderService.getAllOrders();

        Assert.assertEquals(listFromDB.size(),listFromMethod.size());
    }
}
