package hei.td;

import java.time.Instant;
import java.util.Objects;

public class Product {
    private final int id;
    private final String name;
    private final Instant creationDateTime;
    private final Category category;

    public String getCategoryName() {
        return this.category.getName();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public Product(int id, String name, Instant creationDateTime, Category category) {
        this.id = id;
        this.name = name;
        this.creationDateTime = creationDateTime;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", categoryName='" + category.getName() + '\'' +
                '}';
    }
}
