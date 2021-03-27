package test;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBtest {

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testConnection(){

        try {
            Connection c = DriverManager.getConnection("jdbc:mariadb://localhost/tobi_spring","root","");

            System.out.println(c);

            if(c != null){
                System.out.println("Db connection success");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }


    }
}
