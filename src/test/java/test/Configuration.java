package test;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition
        .ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import test.model.ConfigModel;

/**
 *
 */
@org.springframework.context.annotation.Configuration
@ConditionalOnExpression("${dynamic.flag}")
public class Configuration {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ConfigModel configModel;

    @Bean
    public DruidDataSource runConfig(){
        ConfigurableApplicationContext
                context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory
                factorys = (DefaultListableBeanFactory) context.getBeanFactory();
        BeanDefinitionBuilder builder
                = BeanDefinitionBuilder.rootBeanDefinition(DruidDataSource.class);
      /*  builder.addPropertyValue("driver",configModel.getClassName());
        builder.addPropertyValue("username",configModel.getUsername());
        builder.addPropertyValue("password",configModel.getPassword());
        builder.addPropertyValue("url",configModel.getUrl());*/

        factorys.registerBeanDefinition("datasource",
                builder.getBeanDefinition());
        return (DruidDataSource) applicationContext.getBean("datasource");
    }

}
