package org.sql.hibernate.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sql.hibernate.connect.HibernateUtil;
import org.sql.hibernate.dao.UserDAO;
import org.sql.hibernate.entity.User;

import java.util.List;

public class UserService extends HibernateUtil implements UserDAO {
    @Override
    public void create(User user) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            session.save(user);
            transaction.commit();

            logMessage("Користувач добавився в БД під ідентифікатором - "+user.getId());
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        Transaction transaction = null;
        List list = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            String hql = "FROM User AS u";
            Query query = session.createQuery(hql);
            list = query.list();

            transaction.commit();
            logMessage("Повернули всіх користувачів з БД");
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
        return list;
    }

    @Override
    public User getById(int id) {
        Transaction transaction = null;
        User user = new User();
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            String hql = "FROM User AS u WHERE u.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            List<User> list = query.list();
            for (int i = 0; i < list.size(); i++) {
                user = list.get(i);
            }
            transaction.commit();
            logMessage("Повернули користувача з БД під ідентифікатором - "+id);
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
        return user;
    }

    @Override
    public void update(User user, int id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            String hql = "UPDATE User SET name = :name, surname = :surname WHERE id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("name", user.getName());
            query.setParameter("surname", user.getSurname());
            query.setParameter("id", id);

            query.executeUpdate();
            transaction.commit();
            logMessage("Дані користувача змінились під ідентифікатором - "+id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            String hql = "DELETE FROM User WHERE id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id",id);

            query.executeUpdate();
            transaction.commit();
            logMessage("Видалено користувача під ідентифікатором - "+id);
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
    }
}
