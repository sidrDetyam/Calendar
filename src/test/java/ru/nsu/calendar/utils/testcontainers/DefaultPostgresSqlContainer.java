package ru.nsu.calendar.utils.testcontainers;

import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class DefaultPostgresSqlContainer extends PostgreSQLContainer<DefaultPostgresSqlContainer> {

    public static final String POSTGRES_IMAGE_NAME = "postgres:14.2";
    public static final String DB_NAME = "calendar";
    public static final String USERNAME = "calendar_owner";
    public static final String PASSWORD = "calendar_owner";
    public static final Integer POSTGRES_PORT = 5432;

    private static DefaultPostgresSqlContainer postgres;

    public static DefaultPostgresSqlContainer getInstance() {
        if (postgres == null) {
            postgres = new DefaultPostgresSqlContainer();
            postgres.start();
        }

        return postgres;
    }

    public DefaultPostgresSqlContainer() {
        super(POSTGRES_IMAGE_NAME);
        super.withDatabaseName(DB_NAME)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withExposedPorts(POSTGRES_PORT)
                .withReuse(true) //используй это свойство для более быстрого тестирования на локальной машине (don't use in CI/CD)
                .withTmpFs(Map.of("/var", "rw")); //тестовая база хранится не на диске, а в оперативной памяти
    }
}
