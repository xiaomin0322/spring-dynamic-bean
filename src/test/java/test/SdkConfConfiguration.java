package test;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

/**
 * PACKAGE_NAME: test
 * USER :  Administrator
 * DATE :  2018/1/18
 */
@Configuration
public class SdkConfConfiguration  implements EnvironmentAware , ImportBeanDefinitionRegistrar {

    private static Logger logger = LoggerFactory.getLogger(SdkConfConfiguration.class);

    private static String DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

    private DruidDataSource druidDataSource;

    /**
     * 构建数据源
     * @param map
     * @return
     */
    public DruidDataSource buildDataSource(Map<Object,Object> map){
        Object type = map.get("type");
        if( type==null ){
            type = DATASOURCE_TYPE_DEFAULT;
        }
        Class<? extends DataSource> dataSourceType ;
        try {
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String url = map.get("url").toString();
            String userName = map.get("username").toString();
            String passWrod = map.get("password").toString();
            String driver = map.get("className").toString();
            DataSourceBuilder factory =
                    DataSourceBuilder.create().driverClassName(driver).url(url).username(userName)
                    .password(passWrod);
            return (DruidDataSource) factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  null;

    }

    @Override
    public void setEnvironment(Environment environment) {
        initDefaultDataSource(environment);
    }

    /**
     * 加载主要的配置
     */
    public void  initDefaultDataSource(Environment environment){
        RelaxedPropertyResolver relaxedPropertyResolver
                = new RelaxedPropertyResolver(environment,"dynamic.spring.datasource.");
        Map<Object,Object> map = new HashMap<>();
        map.put("type",relaxedPropertyResolver.getProperty("dynamic.spring.datasource.class"));
        map.put("url",relaxedPropertyResolver.getProperty("dynamic.spring.datasource.url"));
        map.put("username",relaxedPropertyResolver.getProperty("dynamic.spring.datasource.username"));
        map.put("password",relaxedPropertyResolver.getProperty("dynamic.spring.datasource.password"));
        map.put("className",relaxedPropertyResolver.getProperty("dynamic.spring.datasource.driver-class-name"));
        druidDataSource = buildDataSource(map);

    }

    /**
     * 注册bean
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        List<Resource> resourceLst = new ArrayList<>();
        try {
            PathMatchingResourcePatternResolver
                    resolver = new PathMatchingResourcePatternResolver();
            List<Resource> localResource =
                    Arrays.asList(resolver.getResources("classpath*:config/*.properties"));
            logger.info("load localAllResource" + localResource);
            if(!CollectionUtils.isEmpty(localResource)){
                resourceLst.addAll(localResource);
            }

            //根据ID 选择优先加载
            /**
             * 读取配置文件
             * 根据dynamic.spring.datasource.id
             * 选择加载 直接配置数据源
             */
            resourceLst.stream().forEach(resource -> {
                try {
                    Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                    if ("datasource".equals(properties.getProperty("dynamic.spring.datasource.id"))){
                        RelaxedDataBinder dataBinder = new RelaxedDataBinder(druidDataSource);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
