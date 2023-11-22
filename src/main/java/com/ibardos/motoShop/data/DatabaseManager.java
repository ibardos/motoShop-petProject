package com.ibardos.motoShop.data;

import org.postgresql.ds.PGSimpleDataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents a database connection to a PostgreSQL database.
 */
@Configuration
public class DatabaseManager {
    /**
     * Creates connection to a PostgreSQL database.
     * @return DataSource object, representing a PSQL database.
     */
    @Bean(name = {"DataSource", "getDataSource"})
    public DataSource getDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName("motoShop");
        dataSource.setUser("ibardos_GitHub_demoProject");
        dataSource.setPassword("Asd123");

        // Test DB connection.
        System.out.println("Connecting...");

        try {
            // Open and close connection.
            dataSource.getConnection().close();
        } catch (SQLException e) {
            throw new DataAccessResourceFailureException("Database connection failed!");
        }

        // If no Exceptions are thrown, the connection test was successful.
        System.out.println("Connection OK");

        return dataSource;
    }

    /**
     * Initializes database with predefined tables and adds initial set of data.
     */
    public static void initializeDatabase() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.ibardos.motoShop.data");
        context.refresh();

        DataSource dataSource = (DataSource) context.getBean("DataSource");

        Connection connection;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessResourceFailureException("Database connection failed during initialization!");
        }

        Resource schemaResource = new FileSystemResource("src/main/resources/schema.sql");
        Resource dataResource = new FileSystemResource("src/main/resources/data.sql");

        ScriptUtils.executeSqlScript(connection, schemaResource);
        ScriptUtils.executeSqlScript(connection, dataResource);
    }
}
