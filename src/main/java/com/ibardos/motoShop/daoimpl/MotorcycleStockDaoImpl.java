package com.ibardos.motoShop.daoimpl;

import com.ibardos.motoShop.model.MotorcycleStock;
import com.ibardos.motoShop.dao.MotorcycleModelDao;
import com.ibardos.motoShop.dao.MotorcycleStockDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object implementation of MotorcycleStock type.
 */
@Component
public class MotorcycleStockDaoImpl implements MotorcycleStockDao {
    DataSource dataSource;
    MotorcycleModelDao motorcycleModelDao;

    @Autowired
    public MotorcycleStockDaoImpl(DataSource dataSource, MotorcycleModelDao motorcycleModelDao) {
        this.dataSource = dataSource;
        this.motorcycleModelDao = motorcycleModelDao;
    }


    @Override
    public MotorcycleStock add(MotorcycleStock motorcycleStock) {
        setCalculatedFieldsOfMotorcycleStockObjectFromClient(motorcycleStock);

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO motorcycle_stock (motorcycle_model_id, mileage, purchasing_price, profit_margin, profit_on_unit, selling_price, in_stock, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParamsForPreparedStatement(motorcycleStock, statement);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            motorcycleStock.setId(rs.getInt(1));

            return motorcycleStock;

        } catch (SQLException e) {
            throw new RuntimeException("Error while adding new MotorcycleStock.", e);
        }
    }

    @Override
    public MotorcycleStock get(Integer id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, motorcycle_model_id, mileage, purchasing_price, profit_margin, profit_on_unit, selling_price, in_stock, color FROM motorcycle_stock WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            // Test if query returns a valid record from DB. If not, return null.
            if (!rs.next()) { return null; }

            // Return MotorcycleStock object created from DB resultSet.
            return createMotorcycleStockObjectFromDbResultSet(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error while getting MotorcycleStock with ID: " + id + ".", e);
        }
    }

    @Override
    public List<MotorcycleStock> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, motorcycle_model_id, mileage, purchasing_price, profit_margin, profit_on_unit, selling_price, in_stock, color FROM motorcycle_stock ORDER BY id";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            // Test if query returns a valid set of data from DB. If not, return null.
            if (!rs.next()) { return null; }

            List<MotorcycleStock> result = new ArrayList<>();

            // Read while there is a next record in the ResultSet. Use do-while, as with the previous test the ResultSet is already stepped once with next().
            do {
                MotorcycleStock motorcycleStock = createMotorcycleStockObjectFromDbResultSet(rs);
                result.add(motorcycleStock);
            } while (rs.next());

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Error while getting all MotorcycleStock", e);
        }
    }

    @Override
    public void update(MotorcycleStock motorcycleStock) {
        setCalculatedFieldsOfMotorcycleStockObjectFromClient(motorcycleStock);

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE motorcycle_stock SET motorcycle_model_id = ?, mileage = ?, purchasing_price = ?, profit_margin = ?, profit_on_unit = ?, selling_price = ?, in_stock = ?, color = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            setParamsForPreparedStatement(motorcycleStock, statement);
            statement.setInt(9, motorcycleStock.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error while updating MotorcycleStock with ID: " + motorcycleStock.getId() + ".", e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM motorcycle_stock WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting MotorcycleStock with ID: " + id + ".", e);
        }
    }


    // Private helper methods
    /**
     * Sets the values of those fields, that are need to be calculated, if MotorcycleStock model is coming from client.
     * @param motorcycleStock object from client, front-end.
     */
    private void setCalculatedFieldsOfMotorcycleStockObjectFromClient(MotorcycleStock motorcycleStock) {
        BigDecimal purchasingPrice = motorcycleStock.getPurchasingPrice();
        Float profitMargin = motorcycleStock.getProfitMargin();
        BigDecimal profitOnUnit = BigDecimal.valueOf(Math.ceil(Double.parseDouble(String.valueOf(purchasingPrice.multiply(BigDecimal.valueOf(profitMargin))))/100)*100);
        BigDecimal sellingPrice = purchasingPrice.add(profitOnUnit);

        motorcycleStock.setProfitOnUnit(profitOnUnit);
        motorcycleStock.setSellingPrice(sellingPrice);
    }

    /**
     * Sets the parameters of a PreparedStatement, to create an injection-proof SQL query.
     * @param motorcycleStock object containing data, which will act as parameters for SQL query.
     * @param statement as PreparedStatement, which will query the DB.
     * @throws SQLException will be thrown in case of a misconfigured query.
     */
    private static void setParamsForPreparedStatement(MotorcycleStock motorcycleStock, PreparedStatement statement) throws SQLException {
        statement.setInt(1, motorcycleStock.getMotorcycleModel().getId());
        statement.setInt(2, motorcycleStock.getMileage());
        statement.setBigDecimal(3, motorcycleStock.getPurchasingPrice());
        statement.setFloat(4, motorcycleStock.getProfitMargin());
        statement.setBigDecimal(5, motorcycleStock.getProfitOnUnit());
        statement.setBigDecimal(6, motorcycleStock.getSellingPrice());
        statement.setInt(7, motorcycleStock.getInStock());
        statement.setString(8, motorcycleStock.getColor());
    }

    /**
     * Creates a new MotorcycleStock object with data queried from DB.
     * @param rs as ResultSet data coming from DB query.
     * @return a new MotorcycleStock object.
     * @throws SQLException will be thrown in case of a misconfigured query.
     */
    private MotorcycleStock createMotorcycleStockObjectFromDbResultSet(ResultSet rs) throws SQLException {
        MotorcycleStock motorcycleStock = new MotorcycleStock(
                motorcycleModelDao.get(rs.getInt(2)),
                rs.getInt(3),
                rs.getBigDecimal(4),
                rs.getFloat(5),
                rs.getBigDecimal(6),
                rs.getBigDecimal(7),
                rs.getInt(8),
                rs.getString(9)
        );

        motorcycleStock.setId(rs.getInt(1));

        return motorcycleStock;
    }
}
