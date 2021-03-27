package com.test.test.domain;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeSql(final String query)throws SQLException{
        this.workWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection c)
                            throws SQLException {
                        return c.prepareStatement(query);
                    }
                }
        );
    }
    public void workWithStatementStrategy(StatementStrategy st) throws SQLException{
        Connection c = null;
        PreparedStatement ps =null;
        try {
            c = dataSource.getConnection();

            ps = st.makePreparedStatement(c);

            ps.executeUpdate();
        }catch (SQLException e){
            throw e;
        }finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("딱히 뭔가 해줄 수 가 없다.");
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    System.out.println("로그 정도를 남길 수 있다.");
                }
            }
            }
        }
}
