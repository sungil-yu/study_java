package com.test.test.domain;


import com.test.test.MessageFactoryBean;
import com.test.test.service.*;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new EventUserUpgradePolicy();
    }

    @Bean
    public DataSource dataSource() {

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mariadb://localhost:3306/tobi_spring");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;

    }

    @Bean
    public UserServiceImpl userServiceImpl() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserDao(userDao());
        userServiceImpl.setMailSender(mailSender());

        return userServiceImpl;
    }

    @Bean
    public UserService userServiceTx(){
        UserServiceTx userServiceTx = new UserServiceTx();
        userServiceTx.setUserService(userServiceImpl());
        userServiceTx.setTr(transactionManager());

        return userServiceTx;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager jdbcTr = new DataSourceTransactionManager();
        jdbcTr.setDataSource(dataSource());
        return jdbcTr;

    }

    @Bean
    public JavaMailSenderImpl mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("mail.server.com");
        return javaMailSender;
    }

    @Bean
    public MessageFactoryBean message(){
        MessageFactoryBean messageFactoryBean = new MessageFactoryBean();
        messageFactoryBean.setText("Factory Bean");

        return messageFactoryBean;
    }

    @Bean
    public TransactionAdvice transactionAdvice(){
        TransactionAdvice ta = new TransactionAdvice();
        ta.setTransactionManager(transactionManager());
        return ta;
    }

    @Bean
    public NameMatchMethodPointcut pointcut(){
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("upgrade*");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor(){
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(transactionAdvice());
        advisor.setPointcut(pointcut());
        return advisor;
    }

    @Bean
    public ProxyFactoryBean userService(){
        ProxyFactoryBean bean = new ProxyFactoryBean();
        bean.setTarget(userServiceImpl());
        //만약 모든메소드에 적용시킬꺼면 advice만 넣으면 된다.
        bean.setInterceptorNames("transactionAdvisor");
        return bean;
    }


}
