package test.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件实体
 */
@Component
public class ConfigModel {

    @Value("${dynamic.spring.datasource.url}")
    private String url;

    @Value("${dynamic.spring.datasource.username}")
    private String username;

    @Value("${dynamic.spring.datasource.password}")
    private String password;

    @Value("${dynamic.spring.datasource.driver-class-name}")
    private String className;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
