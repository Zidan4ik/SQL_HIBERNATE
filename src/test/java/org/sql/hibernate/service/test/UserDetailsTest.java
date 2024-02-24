package org.sql.hibernate.service.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.sql.hibernate.connect.HibernateUtil;
import org.sql.hibernate.entity.User;
import org.sql.hibernate.entity.UserDetails;
import org.sql.hibernate.service.UserDetailsService;
import org.sql.hibernate.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsTest {
    @Test
    public void create(){
        UserDetailsService userDetailsService = new UserDetailsService();
        UserDetails details = new UserDetails(1,40,"0982370190","st1");

        userDetailsService.create(details);

        User userAfterAct = new UserService().getById(details.getId());
        UserDetails detailsFromDB = userDetailsService.getById(details.getId());

        Assert.assertNotNull(userAfterAct.getUserDetails());

        Assert.assertEquals(details.getId(),detailsFromDB.getId());
        Assert.assertEquals(details.getAge(),detailsFromDB.getAge());
        Assert.assertEquals(details.getPhone(),detailsFromDB.getPhone());
        Assert.assertEquals(details.getJob(),detailsFromDB.getJob());
    }

    @Test
    public void update(){
        UserDetailsService userDetailsService = new UserDetailsService();

        UserDetails userDetails = new UserDetails(1,10,"0982300000","driver");
        userDetailsService.update(userDetails);

        UserDetails detailsAfterAct = userDetailsService.getById(userDetails.getId());

        Assert.assertEquals(detailsAfterAct.getId(), userDetails.getId());
        Assert.assertEquals(detailsAfterAct.getAge(), userDetails.getAge());
        Assert.assertEquals(detailsAfterAct.getPhone(), userDetails.getPhone());
        Assert.assertEquals(detailsAfterAct.getJob(), userDetails.getJob());
    }
    @Test
    public void getById(){
        int id = 0;
        UserDetailsService userDetailsService = new UserDetailsService();
        UserDetails detailsFromDB = userDetailsService.getById(id);

        Assert.assertEquals(detailsFromDB.getId(),id);
        if(detailsFromDB.getId()==0 && detailsFromDB.getAge()<=0 && !detailsFromDB.getPhone().equals("") && !detailsFromDB.getJob().equals("")){
            throw new NullPointerException("Даного користувача не існує в БД");
        }
        Assert.assertTrue("Користувач не існує в БД",detailsFromDB.getId()>0);
    }
    @Test
    public void getAll(){
        UserDetailsService userDetailsService = new UserDetailsService();
        List<UserDetails> details = userDetailsService.getAll();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        List list = session.createNativeQuery("SELECT * FROM user_details").getResultList();

        Assert.assertEquals(list.size(),details.size());
    }
    @Test
    public void delete(){
        int id = 1;
        UserDetailsService userDetailsService = new UserDetailsService();
        UserDetails detailsBefore = userDetailsService.getById(id);
        Assert.assertNotNull("Користувача не знайдено",detailsBefore);
        userDetailsService.delete(id);
        detailsBefore = userDetailsService.getById(id);
        Assert.assertNull("Користувача не було видалено",detailsBefore);

    }
}
