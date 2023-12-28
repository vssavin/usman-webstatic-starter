package com.github.vssavin.usman_webstatic_starter.spring5;

import com.github.vssavin.usmancore.config.UsmanDataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * Configuration of main application database.
 *
 * @author vssavin on 28.12.2023.
 */
@Configuration
@Import(UsmanDataSourceConfig.class)
class DataSourcesConfig {

    private static final Logger log = LoggerFactory.getLogger(DataSourcesConfig.class);

    private DataSource appDataSource;

    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "appDatasource")
    public DataSource appDatasource(DatabaseConfig databaseConfig) {
        log.warn(
                "This is an example appDatasource bean. Please create a DataSource bean named 'appDatasource' yourself.");

        if (appDataSource == null) {
            appDataSource = new EmbeddedDatabaseBuilder().generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .build();
        }

        return appDataSource;
    }

}
