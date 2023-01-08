package com.ibardos.motoShop.data;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Represents a database connection to a PostgreSQL database.
 */
@Component
public class DatabaseManager {
    public DataSource dataSource;

    public DatabaseManager() throws SQLException { this.dataSource = connect(); }


    /**
     * Creates connection to a PostgreSQL database.
     * @return DataSource object, representing a PSQL database connection.
     * @throws SQLException if connection failed.
     */
    private DataSource connect() throws SQLException {
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
}
