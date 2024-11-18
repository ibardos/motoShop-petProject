package com.ibardos.motoShop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import jakarta.annotation.PostConstruct;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Configuration class responsible for initializing the application's database.
 */
@Configuration
public class DatabaseInitializer {
    private final DataSource dataSource;

    @Autowired
    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Initializes database with predefined tables and adds initial set of data.
     */
    public void initializeDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            Resource schemaResource = new FileSystemResource("src/main/resources/schema.sql");
            Resource dataResource = new FileSystemResource("src/main/resources/data.sql");

            ScriptUtils.executeSqlScript(connection, schemaResource);
            ScriptUtils.executeSqlScript(connection, dataResource);

            System.out.println("The database has been initialized successfully!");
        } catch (SQLException e) {
            throw new DataAccessResourceFailureException("Database connection failed during initialization!");
        }
    }

    /**
     * Automatically initializes the database upon server startup.
     */
    @PostConstruct
    public void initializeDatabaseAtServerStart() {
        initializeDatabase();
    }
}
