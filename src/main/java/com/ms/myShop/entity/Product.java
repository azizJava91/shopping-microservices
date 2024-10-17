package com.ms.myShop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ("product"))
@DynamicInsert
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;
    String name;
    String brand;
    String model;
    LocalDateTime manufactureDate;
    BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    @ColumnDefault("1")
    Integer active;

    @OneToOne
    Image image;
}
