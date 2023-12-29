package com.github.vssavin.usman_webstatic_starter.spring5.config;

import com.github.vssavin.usmancore.spring5.config.DefaultSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author vssavin on 29.12.2023.
 */
@Configuration
@ComponentScan({ "com.github.vssavin.usman_webstatic_starter.*" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.github.vssavin.usmancore.*")
@EnableWebSecurity
@Import({ DefaultSecurityConfig.class })
public class ApplicationConfig {

}
