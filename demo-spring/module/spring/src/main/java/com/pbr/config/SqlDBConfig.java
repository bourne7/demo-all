package com.pbr.config;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * 这里的坑有点多
 * 1. 从 3.0 开始， JPA 的支持已经改为了 Jakarta，好在只用替换引用即可，避免了 javax 的使用，我认为这里的原因是版权。
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories
 */
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
public class SqlDBConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    @Primary
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = builder
                .dataSource(dataSource())
                .properties(jpaProperties.getProperties())
                .packages("com.pbr.dao.rdb.entity")
                .build();
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder,
                                                         LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }
}
