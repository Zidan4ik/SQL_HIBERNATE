package org.sql.hibernate.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    private int id;
    private String name;
    private String surname;
    @OneToOne
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;
    @OneToMany
    private List<Order> orders;
    public User() {
    }
    public User(int id, String name, String surname, UserDetails userDetails) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.userDetails = userDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String city) {
        this.surname = city;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + surname + '\'' +
                '}';
    }
}
