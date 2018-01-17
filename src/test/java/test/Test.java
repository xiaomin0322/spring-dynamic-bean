package test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.model.ConfigModel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Configuration.class)
public class Test {

    @Autowired
    ApplicationContext applicationContext;
    @org.junit.Test
    public void test(){
        ConfigModel user = (ConfigModel) applicationContext.getBean("user");
        System.err.println("getClassName: "+user.getClassName());
        System.err.println("password: " + user.getPassword());
        System.err.println("url: " + user.getUrl());
        System.err.println("userName: " + user.getUsername());
    }
}
