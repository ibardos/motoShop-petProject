package com.ibardos.motoShop.service.dao.implementation;

import com.ibardos.motoShop.data.DatabaseManager;
import com.ibardos.motoShop.model.Manufacturer;
import com.ibardos.motoShop.service.dao.ManufacturerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object implementation of Manufacturer type.
 */
@Component
public class ManufacturerDaoJdbc implements ManufacturerDao {
    DataSource dataSource;

    @Autowired
    public ManufacturerDaoJdbc(DatabaseManager databaseManager) {
        this.dataSource = databaseManager.dataSource;
    }


    @Override
    public Manufacturer add(Manufacturer manufacturer) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO manufacturer (name, country, partner_since) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setDate(3, manufacturer.getPartnerSince());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            manufacturer.setId(rs.getInt(1));

            return manufacturer;

        } catch (SQLException e) {
            throw new RuntimeException("Error while adding new Manufacturer.", e);
        }
    }

    @Override
    public Manufacturer get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, name, country, partner_since FROM manufacturer WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            // Test if query returns a valid record from DB. If not, return null.
            if (!rs.next()) { return null; }

            // Create Manufacturer object from DB resultSet.
            Manufacturer manufacturer = new Manufacturer(rs.getString(2), rs.getString(3), rs.getDate(4));
            manufacturer.setId(rs.getInt(1));

            return manufacturer;

        } catch (SQLException e) {
            throw new RuntimeException("Error while getting Manufacturer with ID: " + id + ".", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, name, country, partner_since FROM manufacturer ORDER BY id";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            // Test if query returns a valid set of data from DB. If not, return null.
            if (!rs.next()) { return null; }

            List<Manufacturer> result = new ArrayList<>();

            // Read while there is a next record in the ResultSet. Use do-while, as with the previous test the ResultSet is already stepped once with next().
            do {
                Manufacturer manufacturer = new Manufacturer(rs.getString(2), rs.getString(3), rs.getDate(4));
                manufacturer.setId(rs.getInt(1));
                result.add(manufacturer);
            } while (rs.next());

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Error while getting all Manufacturer", e);
        }
    }

    @Override
    public void update(Manufacturer manufacturer) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE manufacturer SET name = ?, country = ?, partner_since = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setDate(3, manufacturer.getPartnerSince());
            statement.setInt(4, manufacturer.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error while updating Manufacturer with ID: " + manufacturer.getId() + ".", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM manufacturer WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting Manufacturer with ID: " + id + ".", e);
        }
    }
}
