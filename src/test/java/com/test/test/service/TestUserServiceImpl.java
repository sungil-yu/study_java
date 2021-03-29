package com.test.test.service;

import com.test.test.domain.Configuration;
import com.test.test.domain.Level;
import com.test.test.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Configuration.class)
public class TestUserServiceImpl extends UserServiceImpl {

    static private class TestUserServiceException extends RuntimeException {
    }
    private String id;

    List<User> users;

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


    private TestUserServiceImpl(String id) {
        this.id = id;
    }

    @Override
    protected void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) throw new TestUserServiceException();
        super.upgradeLevel(user);
    }

    @Test
    void upgradeAllOrNothing(){
        UserServiceImpl testUserServiceImpl = new TestUserServiceImpl(users.get(3).getId());

        testUserServiceImpl.setUserDao(this.userDao);

        userDao.deleteAll();

        for(User user: users) userDao.add(user);

        try{
            testUserServiceImpl.upgradeLevels();
            fail("TestUserServiceExpcetion expected");
        }catch (TestUserServiceException e){
        }




    }
}
