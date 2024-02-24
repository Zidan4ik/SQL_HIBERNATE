package org.sql.hibernate.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sql.hibernate.connect.HibernateUtil;
import org.sql.hibernate.dao.UserDetailsDAO;
import org.sql.hibernate.entity.User;
import org.sql.hibernate.entity.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsService extends HibernateUtil implements UserDetailsDAO {
    private UserService userService = new UserService();

    @Override
    public void create(UserDetails details) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            User user = userService.getById(details.getId());
            user.setUserDetails(details);

            session.save(details);
            session.update(user);

            transaction.commit();
            logMessage("Створено інформацію про користувача під ідентифікатором - "+details.getId());
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
    }

    @Override
    public List<UserDetails> getAll() {
        Transaction transaction = null;
        List<UserDetails> details = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            details = session.createNativeQuery("SELECT * FROM user_details", UserDetails.class).getResultList();

            transaction.commit();
            logMessage("Отримано список інформації користувачів в БД");
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
        return details;
    }

    @Override
    public UserDetails getById(int id) {
        Transaction transaction = null;
        UserDetails details = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            details = session.get(UserDetails.class, id);
            transaction.commit();
            logMessage("Отримано інформацію користувача під ідентифікатором - "+id);
            return details;
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
        return details;
    }

    @Override
    public void update(UserDetails user) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            if (session.get(UserDetails.class,user.getId())!=null)
                session.merge(user);

            transaction.commit();
            logMessage("Змінено дані про інформацію користувача під ідентифікатором - "+user.getId());
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            User user = session.get(User.class, id);
            user.setUserDetails(null);

            session.update(user);
            session.delete(session.get(UserDetails.class, id));

            transaction.commit();
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
    }
}
