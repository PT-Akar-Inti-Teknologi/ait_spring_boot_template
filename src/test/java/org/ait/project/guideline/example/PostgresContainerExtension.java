package org.ait.project.guideline.example;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create")
public class PostgresContainerExtension implements BeforeAllCallback, AfterAllCallback, ExtensionContext.Store.CloseableResource {

    private final static PostgreSQLContainer pg = (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest").withReuse(true);

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        pg.stop();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
//        pg.withInitScript("src/main/resource/db/migration");
        pg.start();
        setProperties();
    }

    @Override
    public void close() throws Throwable {

    }

    private void setProperties() {
        System.setProperty("spring.datasource.driver-class-name", pg.getDriverClassName());
        System.setProperty("spring.datasource.url", pg.getJdbcUrl());
        System.setProperty("spring.datasource.username", pg.getUsername());
        System.setProperty("spring.datasource.password", pg.getPassword());
    }

//    static class Initializer
//            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
//            TestPropertyValues.of(
//                    "spring.datasource.url=" + pg.getJdbcUrl(),
//                    "spring.datasource.username=" + pg.getUsername(),
//                    "spring.datasource.password=" + pg.getPassword()
//            ).applyTo(configurableApplicationContext.getEnvironment());
//        }
//    }
}
