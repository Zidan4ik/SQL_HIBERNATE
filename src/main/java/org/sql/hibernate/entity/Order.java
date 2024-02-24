package org.sql.hibernate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String list;
    @Column(name = "total_sum")
    private int totalSum;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
    }

    public Order(String list, int totalSum, User user) {
        this.list = list;
        this.totalSum = totalSum;
        this.user = user;
    }

    public Order(int id, String list, int totalSum, User user) {
        this.id = id;
        this.list = list;
        this.totalSum = totalSum;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User orders) {
        this.user = orders;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", list='" + list + '\'' +
                ", totalSum=" + totalSum +
                ", user=" + user +
                '}';
    }
}
