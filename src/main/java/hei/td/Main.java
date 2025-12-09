package hei.td;

import java.sql.SQLException;
import java.time.Instant;

public class Main {
    static void main(String[] args) throws SQLException {
        DataRetriever dataRetriever = new DataRetriever();

        System.out.println("a)");
        System.out.println("");

        dataRetriever.getAllCategories().forEach(category -> {
            System.out.println("ID: " + category.getId() + "|" + "NAME: " + category.getName());
        });

        System.out.println("");
        System.out.println("");
        System.out.println("-------------------------------------------------------------");
        System.out.println("b)");
        System.out.println("");
        System.out.println("");

        dataRetriever.getProductList(1, 10).forEach(product -> {
            System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName());
        });

        System.out.println("");

        dataRetriever.getProductList(1, 5).forEach(product -> {
            System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName());
        });

        System.out.println("");

        dataRetriever.getProductList(1, 3).forEach(product -> {
            System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName());
        });

        System.out.println("");

        dataRetriever.getProductList(1, 2).forEach(product -> {
            System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName());
        });

        System.out.println("");
        System.out.println("");
        System.out.println("-------------------------------------------------------------");
        System.out.println("c)");
        System.out.println("");
        System.out.println("");

        dataRetriever.getProductsByCriteria("Dell", null, null, null)
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });

        System.out.println("");

        dataRetriever.getProductsByCriteria(null, "info", null, null)
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });

        System.out.println("");

        dataRetriever.getProductsByCriteria("iphone", "mobile", null, null)
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });

        System.out.println("");

        dataRetriever.getProductsByCriteria(null, null, Instant.parse("2024-02-01T00:00:00Z"), Instant.parse("2024-03-01T00:00:00Z"))
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });

        System.out.println("");

        dataRetriever.getProductsByCriteria("Samsung", "Bureau", null, null)
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });

        System.out.println("");

        dataRetriever.getProductsByCriteria("Sony", "informatique", null, null)
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });

        System.out.println("");

        dataRetriever.getProductsByCriteria(null, "Audio", Instant.parse("2024-01-01T00:00:00Z"), Instant.parse("2024-01-01T00:00:00Z"))
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });

        System.out.println("");

        dataRetriever.getProductsByCriteria(null, null, null, null)
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });

        System.out.println("");
        System.out.println("");
        System.out.println("-------------------------------------------------------------");
        System.out.println("d)");
        System.out.println("");
        System.out.println("");

        dataRetriever.getProductsByCriteria(null, null, null, null, 1, 10)
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });

        System.out.println("");

        dataRetriever.getProductsByCriteria("Dell", null, null, null, 1, 5)
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });

        System.out.println("");

        dataRetriever.getProductsByCriteria(null, "Informatique", null, null, 1, 10)
                .forEach(product -> {
                    System.out.println("ID: " + product.getId() + " | " + "NAME: " + product.getName() + " | " + "CREATION DATE: " + product.getCreationDateTime() + " | " + "CATEGORY NAME: " + product.getCategoryName());
                });
    }
}
