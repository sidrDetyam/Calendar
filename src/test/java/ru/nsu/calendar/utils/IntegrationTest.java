package ru.nsu.calendar.utils;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.nsu.calendar.utils.testcontainers.FlywayExtension;
import ru.nsu.calendar.utils.testcontainers.PostgresInitializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testcontainers")
@ExtendWith(FlywayExtension.class)
@ContextConfiguration(initializers = PostgresInitializer.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTest {
}
