package de.volkswagen.fooddiary.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam(required = false, name = "contains") String productNameContains) {
        List<ProductDTO> productDTOList;
        if (productNameContains == null) {
            productDTOList = this.productService.findAll();
        } else {
            productDTOList = this.productService.findAllContaining(productNameContains);
        }
        return productDTOList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable final String id) {
        return ResponseEntity.ok(this.productService.get(id));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid final ProductDTO productDTO) {
        return new ResponseEntity<>(this.productService.create(productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable final String id, @RequestBody @Valid final ProductDTO productDTO) {
        this.productService.update(id, productDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final String id) {
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/update")
    public ResponseEntity<Void> updateProductsFromApi() {
        this.productService.updateProductsFromApi();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateProductsFromList(@RequestBody @Valid final ProductListResponse productListResponse) {
        this.productService.createAll(productListResponse.getProducts());
        return ResponseEntity.ok().build();
    }

}
