package com.ibardos.motoShop.daoimpl;

import com.ibardos.motoShop.model.MotorcycleModel;
import com.ibardos.motoShop.dao.ManufacturerDao;
import com.ibardos.motoShop.dao.MotorcycleModelDao;
import com.ibardos.motoShop.util.MotorcycleModelType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object implementation of MotorcycleModel type.
 */
@Component
public class MotorcycleModelDaoImpl implements MotorcycleModelDao {
    private final DataSource dataSource;
    private final ManufacturerDao manufacturerDao;

    @Autowired
    public MotorcycleModelDaoImpl(DataSource dataSource, ManufacturerDao manufacturerDao) {
        this.dataSource = dataSource;
        this.manufacturerDao = manufacturerDao;
    }


    @Override
    public MotorcycleModel add(MotorcycleModel motorcycleModel) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO motorcycle_model (manufacturer_id, model_name, model_year, weight, displacement, horse_power, top_speed, gearbox, fuel_capacity, fuel_consumption_per_100kms, motorcycle_model_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParamsForPreparedStatement(motorcycleModel, statement);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            motorcycleModel.setId(rs.getInt(1));

            return motorcycleModel;

        } catch (SQLException e) {
            throw new RuntimeException("Error while adding new MotorcycleModel.", e);
        }
    }

    @Override
    public MotorcycleModel get(Integer id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, manufacturer_id, model_name, model_year, weight, displacement, horse_power, top_speed, gearbox, fuel_capacity, fuel_consumption_per_100kms, motorcycle_model_type FROM motorcycle_model WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            // Test if query returns a valid record from DB. If not, return null.
            if (!rs.next()) { return null; }

            // Return MotorcycleModel object created from DB resultSet.
            return createMotorcycleModelObjectFromResultSet(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error while getting MotorcycleModel with ID: " + id + ".", e);
        }
    }

    @Override
    public List<MotorcycleModel> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, manufacturer_id, model_name, model_year, weight, displacement, horse_power, top_speed, gearbox, fuel_capacity, fuel_consumption_per_100kms, motorcycle_model_type FROM motorcycle_model ORDER BY id";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            // Test if query returns a valid set of data from DB. If not, return null.
            if (!rs.next()) { return null; }

            List<MotorcycleModel> result = new ArrayList<>();

            // Read while there is a next record in the ResultSet. Use do-while, as with the previous test the ResultSet is already stepped once with next().
            do {
                MotorcycleModel motorcycleModel = createMotorcycleModelObjectFromResultSet(rs);
                result.add(motorcycleModel);
            } while (rs.next());

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Error while getting all MotorcycleModel", e);
        }
    }

    @Override
    public void update(MotorcycleModel motorcycleModel) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE motorcycle_model SET manufacturer_id = ?, model_name = ?, model_year = ?, weight = ?, displacement = ?, horse_power = ?, top_speed = ?, gearbox = ?, fuel_capacity = ?, fuel_consumption_per_100kms = ?, motorcycle_model_type = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            setParamsForPreparedStatement(motorcycleModel, statement);
            statement.setInt(12, motorcycleModel.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error while updating MotorcycleModel with ID: " + motorcycleModel.getId() + ".", e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM motorcycle_model WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting MotorcycleModel with ID: " + id + ".", e);
        }
    }


    // Private helper methods
    /**
     * Sets the parameters of a PreparedStatement, to create an injection-proof SQL query.
     * @param motorcycleModel object containing data, which will act as parameters for SQL query.
     * @param statement as PreparedStatement, which will query the DB.
     * @throws SQLException will be thrown in case of a misconfigured query.
     */
    private void setParamsForPreparedStatement(MotorcycleModel motorcycleModel, PreparedStatement statement) throws SQLException {
        statement.setInt(1, motorcycleModel.getManufacturer().getId());
        statement.setString(2, motorcycleModel.getModelName());
        statement.setInt(3, motorcycleModel.getModelYear());
        statement.setInt(4, motorcycleModel.getWeight());
        statement.setInt(5, motorcycleModel.getDisplacement());
        statement.setInt(6, motorcycleModel.getHorsePower());
        statement.setInt(7, motorcycleModel.getTopSpeed());
        statement.setInt(8, motorcycleModel.getGearbox());
        statement.setFloat(9, motorcycleModel.getFuelCapacity());
        statement.setFloat(10, motorcycleModel.getFuelConsumptionPer100Kms());
        statement.setInt(11, motorcycleModel.getMotorcycleModelType().ordinal());
    }

    /**
     * Creates a new MotorcycleModel object with data queried from DB.
     * @param rs as ResultSet data coming from DB query.
     * @return a new MotorcycleModel object.
     * @throws SQLException will be thrown in case of a misconfigured query.
     */
    private MotorcycleModel createMotorcycleModelObjectFromResultSet(ResultSet rs) throws SQLException {
        MotorcycleModel motorcycleModel = new MotorcycleModel(
                manufacturerDao.get(rs.getInt(2)),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getInt(6),
                rs.getInt(7),
                rs.getInt(8),
                rs.getInt(9),
                rs.getFloat(10),
                rs.getFloat(11),
                MotorcycleModelType.values()[rs.getInt(12)]
        );

        motorcycleModel.setId(rs.getInt(1));

        return motorcycleModel;
    }
}
