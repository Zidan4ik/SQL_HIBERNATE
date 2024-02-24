package org.sql.hibernate.service;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sql.hibernate.connect.HibernateUtil;
import org.sql.hibernate.dao.ProductDAO;
import org.sql.hibernate.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService extends HibernateUtil implements ProductDAO {

    @Override
    public void create(Product product) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            session.save(product);
            transaction.commit();
            logMessage("Добавили продукт в бд під ідентифікатором - "+product.getId());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        Transaction transaction = null;
        List<Product> products = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
            query.select(query.from(Product.class));

            products = session.createQuery(query).getResultList();

            transaction.commit();
            logMessage("Повернули список продуктів в БД");
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
        return products;
    }

    @Override
    public Product getById(int id) {
        Transaction transaction = null;
        Product product = new Product();
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root).where(cb.equal(root.get("id"), id));

            product = session.createQuery(query).getSingleResult();
            transaction.commit();
            logMessage("Отримано продукт під ідентифікатором - "+id);
        } catch (Exception exception) {
                logMessage(exception.getMessage());
        }
        return product;
    }

    @Override
    public void update(Product product, int id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate criteriaUpdate = cb.createCriteriaUpdate(Product.class);
            Root<Product> root = criteriaUpdate.from(Product.class);

            criteriaUpdate.set("name",product.getName());
            criteriaUpdate.set("price",product.getPrice());
            criteriaUpdate.where(cb.equal(root.get("id"),id));

            session.createQuery(criteriaUpdate).executeUpdate();

            transaction.commit();
            logMessage("Оновлено продукт під ідентифікатором - "+id);
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

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete criteriaDelete = cb.createCriteriaDelete(Product.class);
            Root<Product> root = criteriaDelete.from(Product.class);

            criteriaDelete.where(cb.equal(root.get("id"),id));
            session.createQuery(criteriaDelete).executeUpdate();

            transaction.commit();
            logMessage("Видалено продукт під ідентифікатором - "+id);
        } catch (Exception exception) {
            logMessage(exception.getMessage());
        }
    }
}
