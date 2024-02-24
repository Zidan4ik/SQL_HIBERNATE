package org.sql.hibernate.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sql.hibernate.connect.HibernateUtil;
import org.sql.hibernate.dao.OrderDAO;
import org.sql.hibernate.entity.Order;
import org.sql.hibernate.entity.User;

import java.util.List;

public class OrderService extends HibernateUtil implements OrderDAO {

    @Override
    public void create(int userId) {
        Transaction transaction = null;
        String list = "";
        int sum = 0;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            User user = session.get(User.class, userId);
            for (int i = 0; i < user.getProducts().size(); i++) {
                list += user.getProducts().get(i).getName() + ", ";
                sum += user.getProducts().get(i).getPrice();
            }
            user.getProducts().clear();
            list = list.substring(0, list.length() - 2);


            Order order = new Order(list, sum, user);
            session.save(order);
            transaction.commit();
            logMessage("Продукти з кошику добавились в замовлення користувача під ідентифікатором - "+userId);
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
    }

    @Override
    public List<Order> getAllByUser(int userId) {
        Transaction transaction = null;
        List<Order> orders = null;
        String hql = "FROM Order WHERE user.id= :id";
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            Query query = session.createQuery(hql);
            query.setParameter("id", userId);

            orders = query.getResultList();
            logMessage("Повернули список замовлень користувача під ідентифікатором - "+userId);
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        Transaction transaction = null;
        List<Order> orders = null;
        String hql = "FROM Order";
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            Query query = session.createQuery(hql);
            orders = query.getResultList();

            transaction.commit();
            logMessage("Повернули всі замовлення з БД");
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
        return orders;
    }
}
