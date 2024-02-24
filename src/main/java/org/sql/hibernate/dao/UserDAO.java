package org.sql.hibernate.dao;

import org.sql.hibernate.entity.User;

import java.util.List;

public interface UserDAO {
    void create(User user);
    List<User> getAll();
    User getById(int id);
    void update(User user, int id);
    void delete(int id);
}
