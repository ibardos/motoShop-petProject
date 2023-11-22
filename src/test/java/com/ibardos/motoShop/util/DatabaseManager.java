package com.ibardos.motoShop.util;

import org.postgresql.ds.PGSimpleDataSource;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database management class for JUnit tests.
 */
public class DatabaseManager {
    /**
     * Initializes database with predefined tables and adds initial set of data.
     */
    public static void initializeDatabase() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName("motoShop");
        dataSource.setUser("ibardos_GitHub_demoProject");
        dataSource.setPassword("Asd123");

        try {
            System.out.println("Connecting...");

            Connection connection = dataSource.getConnection();

            System.out.println("Connection OK");

            Resource schemaResource = new FileSystemResource("src/main/resources/schema.sql");
            Resource dataResource = new FileSystemResource("src/main/resources/data.sql");

            ScriptUtils.executeSqlScript(connection, schemaResource);
            ScriptUtils.executeSqlScript(connection, dataResource);

            System.out.println("Initialization complete");
            System.out.println("Closing connection...");

            connection.close();

            System.out.println("Connection closed!");
        } catch (SQLException e) {
            throw new DataAccessResourceFailureException("Database connection failed during initialization!");
        }
    }
}
