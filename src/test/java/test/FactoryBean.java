package test;


import com.test.test.Message;
import com.test.test.domain.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Configuration.class})
public class FactoryBean {

    @Autowired
    ApplicationContext context;

    @Test
    void getMessageFromFactoryBean() throws NoSuchMethodException {
        Object message = context.getBean("message");

        assertEquals(message, equalTo(Message.class));

        assertEquals(((Message)message).getClass().getMethod("getText"),"Factory Bean");
    }
}
