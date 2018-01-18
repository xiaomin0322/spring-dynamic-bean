package test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.model.ConfigModel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SdkConfConfiguration.class)
public class Test {


    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    PropertySourcesPlaceholderConfigurer ppc;
    @org.junit.Test
    public void test(){
        System.err.println("ppc : " + ppc.getAppliedPropertySources());

    }
}
