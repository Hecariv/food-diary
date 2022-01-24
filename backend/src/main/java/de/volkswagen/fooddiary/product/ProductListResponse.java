package de.volkswagen.fooddiary.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.*;

@NoArgsConstructor @Getter @Setter
public class ProductListResponse {

    private Integer count;
    private Integer page;
    @JsonProperty("page_count")
    private Integer pageCount;
    @JsonProperty("page_size")
    private Integer pageSize;
    private List<ProductDTO> products;
    private Integer skip;

}
