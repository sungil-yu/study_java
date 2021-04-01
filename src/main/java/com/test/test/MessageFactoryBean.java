package com.test.test;

import org.springframework.beans.factory.FactoryBean;

public class MessageFactoryBean implements FactoryBean {

    String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Object getObject() throws Exception {
        return Message.newMessage(this.text);
    }

    @Override
    public Class<?> getObjectType() {
        return Message.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
