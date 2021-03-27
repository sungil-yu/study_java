package test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class JUnitTets {

    static Set<JUnitTets> testObject = new HashSet<>();

    @Test void test1(){
        Assertions.assertNotEquals(this,testObject.contains(this));
        testObject.add(this);
    }
    @Test void test2(){
        Assertions.assertNotEquals(this,testObject.contains(this));
        testObject.add(this);
    }
    @Test void test3(){
        Assertions.assertNotEquals(this,testObject.contains(this));
        testObject.add(this);
    }

}
