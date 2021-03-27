package test;

import com.test.test.domain.Configuration;
import com.test.test.domain.Level;
import com.test.test.domain.User;
import com.test.test.domain.UserDao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Configuration.class)
public class UserDaoTest {


    @Autowired
    private UserDao dao;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp(){
        this.user1 = new User("gyumee", " 방설철", "springno1", Level.BASIC, 1, 0);
        this.user2 = new User("leegw700", " 이길원", "123", Level.SILVER, 55, 10);
        this.user3 = new User("bumjin", " 박범진", "4123", Level.GOLD, 100, 40);

    }

    @Test
    void update(){
        dao.deleteAll();

        dao.add(user1); //수정할 사용자
        dao.add(user2); //수정하지 않을 사용자

        user1.setName("수정네임");
        user1.setPassword("springno6");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        dao.update(user1);

        User user2same = dao.get(user2.getId());
        assertEquals(user2, user2same);

        User updateUser1 = dao.get(user1.getId());
            assertEquals(user1.getLevel().intValue(),updateUser1.getLevel().intValue());
    }



    @Test
     void duplicateKey(){
        dao.deleteAll();


        assertThrows(DuplicateKeyException.class, ()-> {

            dao.add(user1);
            dao.add(user1);

        });

    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {

        dao.deleteAll();
        assertEquals(dao.getCount(),0);



        dao.add(user1);
        dao.add(user2);
        assertEquals(dao.getCount(),2);

        User userget1 = dao.get(user1.getId());
        assertAll(
                ()-> assertEquals(userget1.getName(), user1.getName()),
                ()-> assertEquals(userget1.getPassword(),user1.getPassword())
        );

        User userget2 = dao.get(user2.getId());
        assertAll(
                () -> assertEquals(userget2.getName(), user2.getName()),
                ()-> assertEquals(userget2.getPassword(),user2.getPassword())
        );

    }

    @Test
    void getUserFailure() throws SQLException {

        dao.deleteAll();
        assertEquals(dao.getCount(),0);

        assertThrows(EmptyResultDataAccessException.class,()->{
            dao.get("unknown_id");
        });


    }

}
