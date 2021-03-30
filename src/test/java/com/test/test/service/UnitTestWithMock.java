package com.test.test.service;

import com.test.test.domain.Level;
import com.test.test.domain.User;
import com.test.test.domain.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.test.test.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.test.test.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;


public class UnitTestWithMock {


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
    @Test
    void mockUpgrade() throws Exception{


        UserServiceImpl userServiceImpl = new UserServiceImpl();


        //mock Object
        UserDao mockUserDao = mock(UserDao.class);

        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));

        assertEquals(users.get(1).getLevel(), Level.SILVER);
        verify(mockUserDao).update(users.get(3));
        assertEquals(users.get(3).getLevel(), Level.GOLD);

        ArgumentCaptor<SimpleMailMessage> mailMessageArgument
                = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender, times(2)).send(mailMessageArgument.capture());

    }

}
