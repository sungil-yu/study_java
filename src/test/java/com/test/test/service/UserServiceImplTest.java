package com.test.test.service;

import com.test.test.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

//상수

import static com.test.test.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.test.test.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Configuration.class)
public class UserServiceImplTest {

     @Autowired
     UserServiceImpl userServiceImpl;

    @Autowired
    UserService userservice;

    List<User> users;

    static class MockUserDao implements UserDaoInterface {

        private List<User> users;
        private List<User> updated = new ArrayList<>();

        public MockUserDao(List<User> users) {
            this.users = users;
        }

        @Override
        public void add(User user) {
        }

        @Override
        public User get(String id) {
            return null;
        }

        @Override
        public List<User> getAll() {
            return null;
        }

        @Override
        public void deleteAll() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public void update(User user1) {

        }
    }


    @BeforeEach
    void setUp(){
        users = Arrays.asList(
                new User("seounwoo", "최승우", "springno1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0,"chinggin@naver.com"),
                new User("mungcheul", "신명철", "springno2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0,"chinggin@naver.com"),
                new User("sungwoo", "양성우", "springno3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1,"chinggin@naver.com"),
                new User("jisuk", "이지석", "springno4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD,"chinggin@naver.com"),
                new User("yeounho", "최윤호", "springno5", Level.GOLD, 100, Integer.MAX_VALUE,"chinggin@naver.com")

        );
    }

    @Test
    void bean(){
        assertNotNull(userServiceImpl);
        assertNotNull(userServiceImpl.userDao);
    }

    @Test
    void upgradeLevels(){

        userServiceImpl.userDao.deleteAll();

        for(User user: users) userServiceImpl.userDao.add(user);

        assertEquals(Level.BASIC,users.get(1).getLevel());
        userServiceImpl.upgradeLevels();

        User updateUser = userServiceImpl.userDao.get(users.get(1).getId());

        assertEquals(Level.SILVER,updateUser.getLevel());


    }

}