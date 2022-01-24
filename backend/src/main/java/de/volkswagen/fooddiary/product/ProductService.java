package de.volkswagen.fooddiary.product;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String API_DE_URL = "https://de.openfoodfacts.org?json=true&action=process";
    private static final String API_FIELDS = "&fields=code,brands,product_name,generic_name,product_quantity,energy-kcal_100g,nutriscore_score,image_thumb_url";
    private static final String API_PAGE_SIZE = "&page_size=1000";

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    public List<ProductDTO> findAll() {
        return this.productRepository.findAll()
                .stream()
                .map(product -> this.mapToDto(product, new ProductDTO()))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findAllContaining(String productNameContains) {
        return this.productRepository.findProductsByProductNameContains(productNameContains)
                .stream()
                .map(product -> this.mapToDto(product, new ProductDTO()))
                .collect(Collectors.toList());
    }

    public ProductDTO get(final String id) {
        return this.productRepository.findById(id)
                .map(product -> this.mapToDto(product, new ProductDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final ProductDTO productDTO) {
        final Product product = this.mapToEntity(productDTO, new Product());
        return this.productRepository.save(product).getCode();
    }

    public void createAll(final List<ProductDTO> productDTOList) {
        final List<Product> productList = productDTOList.stream().map(productDTO -> this.mapToEntity(productDTO, new Product())).collect(Collectors.toList());
        this.productRepository.saveAll(productList);
    }

    public void update(final String id, final ProductDTO productDTO) {
        final Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.mapToEntity(productDTO, product);
        this.productRepository.save(product);
    }

    public void delete(final String id) {
        this.productRepository.deleteById(id);
    }

    public void updateProductsFromApi() {
        final ProductListResponse productListResponse = this.getProductListFromApi(1);
        final Integer totalProducts = productListResponse.getCount();
        final Integer pageSize = productListResponse.getPageSize();
        this.createAll(productListResponse.getProducts());
        for(int i = 2; i <= totalProducts/pageSize; i++) {
            this.createAll(this.getProductListFromApi(i).getProducts());
        }
    }

    private ProductListResponse getProductListFromApi(int page) {
        final ProductListResponse productListResponse = this.restTemplate.getForObject( API_DE_URL+API_FIELDS+API_PAGE_SIZE+"&page=" + page, ProductListResponse.class);
        assert productListResponse != null;
        return productListResponse;
    }

    private ProductDTO mapToDto(final Product product, final ProductDTO productDTO) {
        this.modelMapper.map(product, productDTO);
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        this.modelMapper.map(productDTO, product);
        return product;
    }

}
