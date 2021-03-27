package com.test.test.domain;

import java.util.List;

public interface UserDaoInterface {

    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
    void update(User user1);
}
