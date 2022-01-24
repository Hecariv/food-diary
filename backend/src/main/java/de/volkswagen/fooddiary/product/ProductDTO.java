package de.volkswagen.fooddiary.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor @Getter @Setter
public class ProductDTO {

    private String code;
    private String brands;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("generic_name")
    private String genericName;
    @JsonProperty("product_quantity")
    private Integer productQuantity;
    @JsonProperty("energy-kcal_100g")
    private Integer energyKcal100g;
    @JsonProperty("nutriscore_score")
    private Integer nutriscoreScore;
    @JsonProperty("image_thumb_url")
    private String imageThumbUrl;

}
