package hei.td;

import java.time.Instant;

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
}
