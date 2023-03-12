package com.ibardos.motoShop.data;

import org.postgresql.ds.PGSimpleDataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
     * @return DataSource object, representing a PSQL database connection.
     * @throws SQLException if connection failed.
     */
    @Bean(name = {"DataSource", "getDataSource"})
    public DataSource getDatasource() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName("motoShop");
        dataSource.setUser("ibardos_GitHub_demoProject");
        dataSource.setPassword("Asd123");

        // Test DB connection.
        System.out.println("Connecting...");
        // Open and close connection.
        dataSource.getConnection().close();
        // If no Exceptions are thrown, the connection test was successful.
        System.out.println("Connection OK");

        return dataSource;
    }

    /**
     * Initialises database with predefined tables and adds initial set of data.
     * @throws SQLException if connection failed.
     */
    public static void initialiseDatabase() throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.ibardos.motoShop.data");
        context.refresh();

        DataSource dataSource = (DataSource) context.getBean("DataSource");

        Connection connection = dataSource.getConnection();

        Resource schemaResource = new FileSystemResource("src/main/resources/schema.sql");
        Resource dataResource = new FileSystemResource("src/main/resources/data.sql");

        ScriptUtils.executeSqlScript(connection, schemaResource);
        ScriptUtils.executeSqlScript(connection, dataResource);
    }
}
