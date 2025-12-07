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

    private List<Product> mapToProducts(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();

        List<Category> categories = findProductCategoriesByProductId(resultSet.getInt("id"));

        for (Category category : categories) {
            products.add(new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getTimestamp("creation_datetime").toInstant(),
                    category
            ));
        }

        return products;
    }

    private List<Category> findProductCategoriesByProductId(int productId) throws SQLException {
        List<Category> categories = new ArrayList<>();

        try (
                Connection connection = DBConnection.getDBConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM product_category WHERE product_id = ?");
        ) {
            ps.setInt(1, productId);


            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    categories.add(mapToCategory(resultSet));
                }
            }
        }
        return categories;
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

    public List<Product> getProductList(int page, int size) throws SQLException {
        List<Product> allProducts = new ArrayList<>();

        String sql = "SELECT * FROM product;";
        try (Connection connection = DBConnection.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {


            while (resultSet.next()) {
                allProducts.addAll(mapToProducts(resultSet));
            }
        }

        return allProducts
                .stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();
    }
}
