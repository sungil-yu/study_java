package com.test.test.service;

import com.test.test.domain.User;
import lombok.Setter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Setter
public class UserServiceTx implements UserService {

    UserService userService;
    PlatformTransactionManager tr;


    @Override
    public void add(User user) {
        userService.add(user);
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = this.tr.getTransaction(new DefaultTransactionDefinition());

        try {
            userService.upgradeLevels();

            this.tr.commit(status);
        } catch (RuntimeException e) {
            this.tr.rollback(status);
            throw e;
        }


    }

}
