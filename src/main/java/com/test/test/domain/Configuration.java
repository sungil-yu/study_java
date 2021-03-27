package com.test.test.domain;


import com.test.test.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public UserDao userDao(){
            UserDao userDao = new UserDao();
            userDao.setDataSource(dataSource());
            return userDao;
    }


    @Bean
    public DataSource dataSource(){

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mariadb://localhost:3306/tobi_spring");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;

    }

   @Bean
    public UserService userService(){
        UserService userService = new UserService();
        userService.setUserDao(userDao());
        return userService;
   }


}
