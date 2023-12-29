package com.tests.lab2;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(initializers = {AuditVizualizationBaseTest.Initializer.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class, SqlScriptsTestExecutionListener.class})
public abstract class AuditVizualizationBaseTest{


    static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

    static{
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer("postgres:12");
                //.withDatabaseName("calculation")
                //.withUsername("postgres")
                //.withPassword("12345");
        POSTGRE_SQL_CONTAINER.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
        public void initialize(ConfigurableApplicationContext configurableApplicationContext){
            TestPropertyValues.of(
                    "database.cache.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                    "database.cache.username=" + POSTGRE_SQL_CONTAINER.getUsername(),
                    "database.cache.password=" + POSTGRE_SQL_CONTAINER.getPassword(),
                    "spring.flyway.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                    "spring.flyway.user=" + POSTGRE_SQL_CONTAINER.getUsername(),
                    "spring.flyway.password=" + POSTGRE_SQL_CONTAINER.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
