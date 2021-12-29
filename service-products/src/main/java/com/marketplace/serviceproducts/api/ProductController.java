package com.marketplace.serviceproducts.api;

import com.marketplace.serviceproducts.api.dto.FeedbackDTO;
import com.marketplace.serviceproducts.api.dto.ProductDTO;
import com.marketplace.serviceproducts.repo.model.Product;
import com.marketplace.serviceproducts.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public final class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> index() {
        final List<Product> products = productService.fetchAll();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> show(@PathVariable long id) {
        try {
            final Product product = productService.fetchByID(id);

            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProductDTO product) {
        final long store = product.getStore();
        final String name = product.getName();
        final BigDecimal price = product.getPrice();
        final boolean discontinued = product.isDiscontinued();
        final String description = product.getDescription();
        final long id = productService.create(store, name, price, discontinued, description);
        final String location = String.format("/product/%d", id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody ProductDTO product) {
        final long store = product.getStore();
        final String name = product.getName();
        final BigDecimal price = product.getPrice();
        final boolean discontinued = product.isDiscontinued();
        final String description = product.getDescription();

        try {
            productService.update(id, store, name, price, discontinued, description);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/feedbacks")
    public ResponseEntity<List<FeedbackDTO>> showProductFeedbacks(@PathVariable long id) {
        try {
            final List<FeedbackDTO> feedbackDTO;
            feedbackDTO = productService.getFeedbacksByProductId(id);

            return ResponseEntity.ok(feedbackDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/rating")
    public ResponseEntity<Double> getPostAverage(@PathVariable long id) {
        final Double rating = productService.getRatingByProductId(id);

        return ResponseEntity.ok(rating);
    }
}
