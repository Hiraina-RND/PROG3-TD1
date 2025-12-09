package hei.td;

import java.sql.*;
import java.time.Instant;
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

        DBConnection dbConnection = new DBConnection(
                "jdbc:postgresql://localhost:5432/product_management_db",
                "product_manager_user",
                "123456"
        );

        try (
                Connection connection = dbConnection.getDBConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT c.id, c.name, c.product_id FROM product_category c WHERE product_id = ?");
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

        DBConnection dbConnection = new DBConnection(
                "jdbc:postgresql://localhost:5432/product_management_db",
                "product_manager_user",
                "123456"
        );

        try (
                Connection connection = dbConnection.getDBConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT c.id, c.name, c.product_id FROM product_category c;");
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

        DBConnection dbConnection = new DBConnection(
                "jdbc:postgresql://localhost:5432/product_management_db",
                "product_manager_user",
                "123456"
        );

        String sql = "SELECT p.id, p.name, p.creation_datetime FROM product p LIMIT ? OFFSET ?;";
        try (
                Connection connection = dbConnection.getDBConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    allProducts.addAll(mapToProducts(resultSet));
                }
            }
        }

        return allProducts;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin, Instant creationMax) throws SQLException {
        List<Product> filteredProducts = new ArrayList<>();

        DBConnection dbConnection = new DBConnection(
                "jdbc:postgresql://localhost:5432/product_management_db",
                "product_manager_user",
                "123456"
        );

        StringBuilder sql = new StringBuilder();

        if (productName != null) {
            sql = new StringBuilder("""
                        SELECT p.id, p.name, p.creation_datetime, c.name AS category_name
                        FROM product p
                        INNER JOIN product_category c
                        ON c.product_id = p.id
                        WHERE 1=1
        """);

            sql.append(" AND p.name ILIKE ? ");

            if (categoryName != null) {
                sql.append(" AND c.name ILIKE ? ");
            }
        } else {
            sql = new StringBuilder("""
                        SELECT p.id, p.name, p.creation_datetime, c.name
                        FROM product p
                        INNER JOIN product_category c
                        ON c.product_id = p.id
                        WHERE 1=1
        """);

            if (categoryName != null) {
                sql.append(" AND c.name ILIKE ? ");
            }
        }

        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ? ");
        }

        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ? ");
        }



        try (
                Connection connection = dbConnection.getDBConnection();
                PreparedStatement ps = connection.prepareStatement(sql.toString());
        ) {

            int paramIndex = 1;

            if (productName != null && categoryName == null) {
                ps.setString(paramIndex++, "%" + productName + "%");
            }

            if (productName != null && categoryName != null) {
                ps.setString(paramIndex++, "%" + productName + "%");
                ps.setString(paramIndex++, "%" + categoryName + "%");
            }

            if (productName == null && categoryName != null) {
                ps.setString(paramIndex++, "%" + categoryName + "%");
            }

            if (creationMin != null) {
                ps.setTimestamp(paramIndex++, Timestamp.from(creationMin));
            }

            if (creationMax != null) {
                ps.setTimestamp(paramIndex++, Timestamp.from(creationMax));
            }

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    filteredProducts.addAll(mapToProducts(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
        return filteredProducts;
    }

    List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin, Instant creationMax, int page, int size) throws SQLException {
        List<Product> filteredProducts = new ArrayList<>();

        DBConnection dbConnection = new DBConnection(
                "jdbc:postgresql://localhost:5432/product_management_db",
                "product_manager_user",
                "123456"
        );

        StringBuilder sql = new StringBuilder();

        if (productName != null) {
            sql = new StringBuilder("""
                        SELECT p.id, p.name, p.creation_datetime, c.name AS category_name
                        FROM product p
                        INNER JOIN product_category c
                        ON c.product_id = p.id
                        WHERE 1=1
        """);

            sql.append(" AND p.name ILIKE ? ");

            if (categoryName != null) {
                sql.append(" AND c.name ILIKE ? ");
            }
        } else {
            sql = new StringBuilder("""
                        SELECT p.id, p.name, p.creation_datetime, c.name
                        FROM product p
                        INNER JOIN product_category c
                        ON c.product_id = p.id
                        WHERE 1=1
        """);

            if (categoryName != null) {
                sql.append(" AND c.name ILIKE ? ");
            }
        }

        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ? ");
        }

        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ? ");
        }

        sql.append(" ORDER BY p.id ");
        sql.append(" LIMIT ? OFFSET ? ");


        try (
                Connection connection = dbConnection.getDBConnection();
                PreparedStatement ps = connection.prepareStatement(sql.toString());
        ) {

            int paramIndex = 1;

            if (productName != null && categoryName == null) {
                ps.setString(paramIndex++, "%" + productName + "%");
            }

            if (productName != null && categoryName != null) {
                ps.setString(paramIndex++, "%" + productName + "%");
                ps.setString(paramIndex++, "%" + categoryName + "%");
            }

            if (productName == null && categoryName != null) {
                ps.setString(paramIndex++, "%" + categoryName + "%");
            }

            if (creationMin != null) {
                ps.setTimestamp(paramIndex++, Timestamp.from(creationMin));
            }

            if (creationMax != null) {
                ps.setTimestamp(paramIndex++, Timestamp.from(creationMax));
            }

            ps.setInt(paramIndex++, size);
            ps.setInt(paramIndex++, page * size);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    filteredProducts.addAll(mapToProducts(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
        return filteredProducts;
    }
}
