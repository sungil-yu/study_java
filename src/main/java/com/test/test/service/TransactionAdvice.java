package com.test.test.service;

import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {
    @Setter
    PlatformTransactionManager transactionManager;


    //MethodInvocation은 타겟을 호출하는 기능을 가진 오브젝트를 프록시로부터 받기떄문에 어드바이스는 특정 타깃에 의존x
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        TransactionStatus status =
                this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {

            Object ret = methodInvocation.proceed();
            this.transactionManager.commit(status);
            return ret;
        } catch (RuntimeException e) {
            this.transactionManager.rollback(status);
            throw e;
        }

    }
}
