package org.sql.hibernate.service.paramaterized;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sql.hibernate.entity.User;
import org.sql.hibernate.service.UserService;


public class ParameterizedTestt {
    @ParameterizedTest
    @CsvSource ({
            "roma,pravnyk",
            "1,2"
    })
    public void create(String name,String surname){
        UserService userService = new UserService();
        User user = new User(name,surname);

        userService.create(user);

        User userFromDB = userService.getById(user.getId());

        Assert.assertEquals(userFromDB.getId(),user.getId());
        Assert.assertEquals(userFromDB.getName(),user.getName());
        Assert.assertEquals(userFromDB.getSurname(),user.getSurname());
    }
}
