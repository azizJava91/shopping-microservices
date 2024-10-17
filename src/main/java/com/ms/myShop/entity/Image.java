package com.ms.myShop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ("image"))
@DynamicInsert
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long imageId;
    String fileName;
    String fileType;
    @Lob
    byte[] imageData;
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    Product product;

}
