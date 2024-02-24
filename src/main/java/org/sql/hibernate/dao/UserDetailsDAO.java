package org.sql.hibernate.dao;

import org.sql.hibernate.entity.UserDetails;

import java.util.List;

public interface UserDetailsDAO {
    void create(UserDetails details);
    List<UserDetails> getAll();
    UserDetails getById(int id);
    void update(UserDetails details);
    void delete(int id);
}
