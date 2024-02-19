package org.sql.hibernate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    private int id;
    private int age;
    private String phone;
    private String job;

    public UserDetails() {
    }

    public UserDetails(int id, int age, String phone, String job) {
        this.id = id;
        this.age = age;
        this.phone = phone;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
