package com.github.vssavin.usman_webstatic_starter.spring5;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Main application database properties.
 *
 * @author vssavin on 28.12.2023.
 */
@Component
@ConfigurationProperties(prefix = "db")
@PropertySource(value = { "file:./conf.properties", "file:../conf.properties", "classpath: application.properties",
        "classpath: application.yml" }, ignoreResourceNotFound = true)
class DatabaseConfig {

    @Value("${db.url:}")
    private String url;

    @Value("${db.driverClass:}")
    private String driverClass;

    @Value("${db.dialect:}")
    private String dialect;

    @Value("${db.name:}")
    private String name;

    @Value("${db.user:}")
    private String user;

    @Value("${db.password:}")
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
