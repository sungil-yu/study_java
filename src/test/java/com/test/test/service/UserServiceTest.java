package com.test.test.service;

import com.test.test.domain.Configuration;
import com.test.test.domain.Level;
import com.test.test.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Configuration.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    List<User> users;


    @BeforeEach
    void setUp(){
        users = Arrays.asList(
                new User("seounwoo", "최승우", "springno1", Level.BASIC, 49, 0),
                new User("mungcheul", "신명철", "springno2", Level.BASIC, 50, 0),
                new User("sungwoo", "양성우", "springno3", Level.SILVER, 60, 29),
                new User("jisuk", "이지석", "springno4", Level.SILVER, 60, 30),
                new User("yeounho", "최윤호", "springno5", Level.GOLD, 100, 100)

        );
    }

    @Test
    void bean(){
        System.out.println(userService);
        System.out.println(userService.userDao);
        assertNotNull(userService);
        assertNotNull(userService.userDao);
    }

    @Test
    void upgradeLevels(){

        userService.userDao.deleteAll();

        for(User user: users){
            userService.userDao.add(user);
        }

        assertEquals(Level.BASIC,users.get(1).getLevel());
        System.out.println(users.get(1));
        userService.upgradeLevels();

        User updateUser = userService.userDao.get(users.get(1).getId());
        System.out.println(updateUser);

        assertEquals(Level.SILVER,updateUser.getLevel());


    }
}