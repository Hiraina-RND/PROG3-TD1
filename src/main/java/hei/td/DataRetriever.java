package hei.td;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private Category mapToCategory(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();

        try (
                Connection connection = DBConnection.getDBConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM product_category;");
                ResultSet resultSet = ps.executeQuery()
        ) {

            while (resultSet.next()) {
                categories.add(mapToCategory(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
        return categories;
    }
}
