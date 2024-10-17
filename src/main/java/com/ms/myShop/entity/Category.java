package com.ms.myShop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ("category"))
@DynamicInsert
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long categoryId;
    @NotNull
    String name;
    @OneToMany(mappedBy = "category")
    List<Product> products;
    @ColumnDefault("1")
    Integer active;

}
