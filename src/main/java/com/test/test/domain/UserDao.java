package com.test.test.domain;

import com.test.test.exception.DuplicateUserIdException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao implements UserDaoInterface{

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setLevel(Level.valueOf(resultSet.getInt("level")));
                    user.setLogin(resultSet.getInt("login"));
                    user.setRecommend(resultSet.getInt("recommend"));

                    return user;

                }
            };

    public void add(final User user) throws DuplicateUserIdException {
        jdbcTemplate.update("insert into users(id,name,password,level,recommend,login) values(?,?,?,?,?,?)"
                , user.getId(), user.getName(), user.getPassword(),user.getLevel().intValue(),user.getRecommend(),user.getLogin());

    }


    public User get(String id) {
      return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[] {id},this.userMapper);

    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id",this.userMapper);
    }

    public int getCount(){

        return this.jdbcTemplate.queryForObject("select count(*) from users",Integer.class);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update("update users set name = ?, password = ?, level =?, login = ? " +
                ", recommend = ? where id = ? ", user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(),user.getId());
    }

    public void deleteAll(){
        this.jdbcTemplate.update("delete from users");
    }



}
