package com.example.demo.product.model;

import com.example.demo.category.model.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private String id; //Can use UUID type

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private Double price;

    @Column(name = "region")
    @Enumerated(EnumType.STRING)
    private Region region;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_value" , referencedColumnName = "value")
    private Category category;

    public Product(ProductRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.manufacturer = request.getManufacturer();
        this.price = request.getPrice();
        this.region = Region.valueOf(request.getRegion());
        this.category = new Category(request.getCategory());
    }
}
