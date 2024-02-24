package org.sql.hibernate.service.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.sql.hibernate.connect.HibernateUtil;
import org.sql.hibernate.entity.User;
import org.sql.hibernate.service.UserService;

import java.util.List;

public class UserTest {
    @Test
    public void create(){
        UserService userService = new UserService();
        User userToDB = new User("Dima","Krasnogor");
        System.out.println(userToDB.getId());
        userService.create(userToDB);
        System.out.println(userToDB.getId());
        Assert.assertTrue("Користувач не добавився в БД",userToDB.getId()>0);
    }
    @Test
    public void getById(){
        int id = 5;
        UserService userService = new UserService();
        User user = userService.getById(id);

        Assert.assertEquals(user.getId(),id);
        if(user.getId()==0 && !user.getName().equals("") && !user.getSurname().equals("")){
            throw new NullPointerException("Даного користувача не існує в БД");
        }
        Assert.assertFalse("Користувач не може мати негативний ідентифікатор",user.getId()<0);
    }

    @Test
    public void getAll(){
        UserService userService = new UserService();
        List<User> users = userService.getAll();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction sessionTransaction = session.getTransaction();
        sessionTransaction.begin();

        List list = session.createNativeQuery("SELECT * FROM users").getResultList();


        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(),list.size());
    }

    @Test
    public void update(){
        int id = 1;
        UserService userService = new UserService();
        User user = new User("Trim","ArmStrong");
        userService.update(user,id);
        User userAfterAct = userService.getById(id);

        Assert.assertEquals(userAfterAct.getId(),id);
        Assert.assertEquals(user.getName(),userAfterAct.getName());
        Assert.assertEquals(user.getSurname(),userAfterAct.getSurname());
    }

    @Test
    public void delete(){
        int id = 5;
        UserService userService = new UserService();
        User userToAct = userService.getById(id);
        Assert.assertTrue("Такого користувача не існує в БД ",userToAct.getId()>0);
        userService.delete(id);
        User userAfterAct = userService.getById(id);
        Assert.assertTrue("Такий користувач існує в БД",userAfterAct.getId()==0);

    }
}
