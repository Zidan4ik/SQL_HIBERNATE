package org.sql.hibernate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private int id;
    private String list;
    @Column(name = "total_sum")
    private int totalSum;
    @ManyToOne
    private User orders;

    public Order() {
    }

    public Order(int id, String list, int totalSum, User orders) {
        this.id = id;
        this.list = list;
        this.totalSum = totalSum;
        this.orders = orders;
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

    public User getOrders() {
        return orders;
    }

    public void setOrders(User orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", list='" + list + '\'' +
                ", totalSum=" + totalSum +
                ", orders=" + orders +
                '}';
    }
}
