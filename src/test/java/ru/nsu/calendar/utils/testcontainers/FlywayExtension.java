package ru.nsu.calendar.utils.testcontainers;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Расширение для очистки базы и запуска миграций после каждого теста
 */
public class FlywayExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) {
        Flyway flyway = SpringExtension.getApplicationContext(context).getBean(Flyway.class);
        flyway.clean();
        flyway.migrate();
    }
}
