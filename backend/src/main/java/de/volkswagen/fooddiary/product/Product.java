package de.volkswagen.fooddiary.product;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
@NoArgsConstructor @Getter @Setter
public class Product implements Serializable {

    @Id
    @Column(nullable = false, updatable = false)
    private String code;
    @Column(columnDefinition = "TEXT")
    private String brands;
    @Column
    private String productName;
    @Column(columnDefinition = "TEXT")
    private String genericName;
    @Column
    private Integer productQuantity;
    @Column
    private Integer energyKcal100g;
    @Column
    private Integer nutriscoreScore;
    @Column
    private String imageThumbUrl;

}
