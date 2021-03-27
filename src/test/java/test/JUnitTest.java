package test;


import com.test.test.domain.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.applet.AppletContext;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Configuration.class)
public class JUnitTest {

    //테스트마다 어플리케이션 di
    @Autowired
    ApplicationContext context;

        static   Set<JUnitTest> jUnitTestSet = new HashSet<>();
        static  ApplicationContext contextObject = null;

        //스프링 애플리케이션 컨텍스트가 하나만 생성되는지 확인 test

    @Test
    void test1(){
        //set구조에 테스트 오브젝트가 존재하지 않는다면
        Assumptions.assumeFalse(jUnitTestSet.contains(this));
        jUnitTestSet.add(this);  //추가한다.

        //처음 테스트경우 null 값이므로 자동 주입받은 context를 contextObject에 추가한다.
        Assertions.assertTrue(contextObject == null || contextObject == this.context);
        //다음 테스트 실행되는 경우 전역 context가  contextObject와 동일해야한다.
        contextObject = this.context;

        }
    @Test
    void test2(){
        //set구조에 테스트 오브젝트가 존재하지 않는다면
        Assumptions.assumeFalse(jUnitTestSet.contains(this));
        jUnitTestSet.add(this);  //추가한다.

        //처음 테스트경우 null 값이므로 자동 주입받은 context를 contextObject에 추가한다.
        //Junit 5 경우 assertThat hamcrest 라이브러리를 사용해서 macther를 사용합니다.
        assertThat(contextObject == null || contextObject == this.context,is(true));
        //다음 테스트 실행되는 경우 전역 context가  contextObject와 동일해야한다.
        contextObject = this.context;

    }@Test
    void test3(){
        //set구조에 테스트 오브젝트가 존재하지 않는다면
        Assumptions.assumeFalse(jUnitTestSet.contains(this));
        jUnitTestSet.add(this);  //추가한다.

        //처음 테스트경우 null 값이므로 자동 주입받은 context를 contextObject에 추가한다.
        //contextObject가 널값이거나 인스턴스 context일경우 통과
        assertThat(contextObject,either(is(nullValue())).or(is(this.context)));
        //다음 테스트 실행되는 경우 전역 context가  contextObject와 동일해야한다.
        contextObject = this.context;

    }


}



