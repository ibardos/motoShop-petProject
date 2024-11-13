package com.ibardos.motoShop.data;

import org.postgresql.ds.PGSimpleDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessResourceFailureException;

import javax.sql.DataSource;

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
}
