package hei.td;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataRetrieverTest {

    private DataRetriever dataRetriever;

    @BeforeEach
    void setUp() {
        dataRetriever = new DataRetriever();
    }

    @Test
    void testGetAllCategories() {
        try {
            List<Category> categories = dataRetriever.getAllCategories();
            assertNotNull(categories,"The list must not be empty");
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testGetProductListPagination() {
        try {
            List<Product> productsPage5 = dataRetriever.getProductList(1, 5);
            List<Product> productsPage2 = dataRetriever.getProductList(1, 2);

            List<Integer> realListOfProduct = new ArrayList<>();

            for (Product product : productsPage5) {
                realListOfProduct.add(product.getId());
            }

            realListOfProduct = realListOfProduct
                    .stream()
                    .distinct()
                    .toList();

            assertNotNull(productsPage5);
            assertNotNull(productsPage2);
            assertEquals(5, realListOfProduct.size());
            assertTrue(productsPage5.size() >= productsPage2.size(), "More products expected for a larger page");

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testGetProductsByCriteria() {
        try {
            List<Product> listOfProducts = dataRetriever.getProductsByCriteria("Dell", null, null, null);

            String categoryNameOfTheProduct = listOfProducts.getFirst().getCategoryName();
            String nameOfTheProduct = listOfProducts.getFirst().getName();

            assertEquals("Informatique", categoryNameOfTheProduct);
            assertEquals("Laptop Dell XPS", nameOfTheProduct);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testProductByCriteriaWithPagination() {
        try {
            List<Product> listOfProducts = dataRetriever.getProductsByCriteria(null, "Info", null, null, 1, 2);
            List<Integer> realListSize = new ArrayList<>();

            for (Product product : listOfProducts) {
                realListSize.add(product.getId());
            }

            realListSize = realListSize
                    .stream()
                    .distinct()
                    .toList();

            assertEquals(2, realListSize.size());
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}

