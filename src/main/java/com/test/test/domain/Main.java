package com.test.test.domain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ApplicationContext context= new AnnotationConfigApplicationContext(Configuration.class);

    }
}
