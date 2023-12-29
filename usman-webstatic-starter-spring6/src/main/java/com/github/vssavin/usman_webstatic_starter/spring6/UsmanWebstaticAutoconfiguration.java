package com.github.vssavin.usman_webstatic_starter.spring6;

import com.github.vssavin.usman_webstatic_core.UsmanWebstaticConfigurer;
import com.github.vssavin.usmancore.config.*;
import com.github.vssavin.usmancore.spring6.config.DefaultSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.*;

/**
 * Usman-webstatic autoconfiguration.
 *
 * @author vssavin on 28.12.2023.
 */
@Configuration
@ComponentScan({ "com.github.vssavin.usmancore.*", "com.github.vssavin.usman_webstatic.spring6.*" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.github.vssavin.usmancore.*" })
@EnableWebSecurity
@Import({ DefaultSecurityConfig.class })
public class UsmanWebstaticAutoconfiguration {

    private static final Logger log = LoggerFactory.getLogger(UsmanWebstaticAutoconfiguration.class);

    @Bean
    public EntityScanPackages usmanEntityPackages() {
        log.debug("Initializing usman entity packages...");
        return () -> new String[] { "com.github.vssavin.usmancore" };
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(JavaMailSender.class)
    public JavaMailSender emailSender() {
        return new JavaMailSenderImpl();
    }

    @Bean
    @ConditionalOnMissingBean(UsmanWebstaticConfigurer.class)
    public UsmanWebstaticConfigurer usmanConfigurer(UsmanUrlsConfigurer urlsConfigurer, OAuth2Config oAuth2Config,
            List<PermissionPathsContainer> permissionPathsContainerList) {
        log.warn(
                "This is an example usman webstatic configurer. Please create UsmanWebstaticConfigurer bean yourself.");

        UsmanWebstaticConfigurer usmanConfigurer = new UsmanWebstaticConfigurer(urlsConfigurer, oAuth2Config,
                permissionPathsContainerList);

        usmanConfigurer.applicationUrl("https://example.com");

        usmanConfigurer.permission(new AuthorizedUrlPermission("/index.html", Permission.ANY_USER))
            .permission(new AuthorizedUrlPermission("/index", Permission.ANY_USER));

        usmanConfigurer.defaultLanguage("en");

        return usmanConfigurer.configure();
    }

    @Bean
    @ConditionalOnMissingBean(UsmanUrlsConfigurer.class)
    public UsmanUrlsConfigurer usmanUrlsConfigurer() {
        log.warn("This is an example usman urls configurer. Please create UsmanUrlsConfigurer bean yourself.");

        UsmanUrlsConfigurer usmanUrlsConfigurer = new UsmanUrlsConfigurer();

        usmanUrlsConfigurer.successUrl("/index.html").adminSuccessUrl("/usman/v1/admin");

        return usmanUrlsConfigurer.configure();
    }

    @Bean
    @ConditionalOnMissingBean(FilterRegistrationBean.class)
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
        FilterRegistrationBean<HiddenHttpMethodFilter> filterBean = new FilterRegistrationBean<>(
                new HiddenHttpMethodFilter());
        filterBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterBean;
    }

    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    @ConditionalOnMissingBean(PersistenceExceptionTranslationPostProcessor.class)
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(LocalContainerEntityManagerFactoryBean.class)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(List<EntityScanPackages> entityScanPackagesList,
            @Qualifier("routingDatasource") DataSource routingDatasource, DatabaseConfig databaseConfig) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        try {
            List<String> packagesList = new ArrayList<>();
            entityScanPackagesList
                .forEach(packages -> packagesList.addAll(Arrays.asList(packages.getPackagesToScan())));
            em.setDataSource(routingDatasource);
            em.setPackagesToScan(packagesList.toArray(new String[0]));

            em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            String hibernateDialect = databaseConfig.getDialect();

            Properties additionalProperties = new Properties();
            additionalProperties.put("hibernate.dialect", hibernateDialect);
            em.setJpaProperties(additionalProperties);
        }
        catch (Exception e) {
            log.error("Creating LocalContainerEntityManagerFactoryBean error!", e);
        }

        return em;
    }

}
