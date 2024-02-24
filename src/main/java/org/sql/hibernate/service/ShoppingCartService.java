package org.sql.hibernate.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sql.hibernate.connect.HibernateUtil;
import org.sql.hibernate.dao.ShoppingCartDAO;
import org.sql.hibernate.entity.Product;
import org.sql.hibernate.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartService extends HibernateUtil implements ShoppingCartDAO {
    @Override
    public void addProductToUser(int userId, int productId) {
        Transaction transaction = null;

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            session.get(User.class,userId).getProducts().add(session.get(Product.class,productId));

            transaction.commit();
            logMessage("Добавлено в кошик продукт - "+productId+" користувача під ідентифікатором - "+userId);
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
    }

    @Override
    public void removeProductFromUser(int userId, int productId) {
        Transaction transaction = null;
        String hql2 = "FROM User WHERE id=:id";
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            Query query = session.createQuery(hql2);
            query.setParameter("id",userId);

            User user = (User) query.getSingleResult();
            for (int i = 0; i < user.getProducts().size(); i++) {
                if(user.getProducts().get(i).getId()==productId){
                    user.getProducts().remove(i);
                    break;
                }
            }
            session.update(user);
            transaction.commit();
            logMessage("Видалено продукт - "+ productId+" користувача під ідентифікатором - "+userId);
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
    }

    @Override
    public List<Product> getAllProductsFromUser(int userId) {
        Transaction transaction = null;
        List<Product> productsId = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

            Root<User> root = query.from(User.class);
            query.select(root).where(criteriaBuilder.equal(root.get("id"),userId));
            User user = session.createQuery(query).getSingleResult();

            productsId = user.getProducts();

            transaction.commit();
            logMessage("Отримано список всіх продуктів користувача під ідентифікатором - "+userId);
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
        return productsId;
    }

    @Override
    public void removeAllProductsFromUser(int userId) {
        Transaction transaction = null;
        String hql2 = "FROM User WHERE id=:id";
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            Query query = session.createQuery(hql2);
            query.setParameter("id",userId);

            User user = (User) query.getSingleResult();
            user.getProducts().clear();
            session.update(user);
            transaction.commit();
            logMessage("Видалено всіх продуктів користувача під ідентифікатором - "+userId);
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
    }
}
