package test.Demo;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

@SpringBootTest
public class TestBeanOne {


    @Test
    public void getBean(){
        BeanDefinitionRegistry reg=new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader =
                new PropertiesBeanDefinitionReader(reg);
        reader.loadBeanDefinitions(new
                ClassPathResource("application1.properties"));
        BeanFactory factory = (BeanFactory) reg;
        TeseBean  one = (TeseBean) factory.getBean("testBean");
        System.out.println(" string :" + one.getString());

        BeanOne one1 = (BeanOne) factory.getBean("childBean");

        BeanTwo two = (BeanTwo) factory.getBean("commonBean");
        System.out.println(" string1 :" + one1.getString());
        System.out.println(" string 2:" + two.getString());



    }
}
