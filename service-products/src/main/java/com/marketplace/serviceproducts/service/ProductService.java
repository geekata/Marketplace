package com.marketplace.serviceproducts.service;

import com.marketplace.serviceproducts.api.dto.FeedbackDTO;
import com.marketplace.serviceproducts.repo.ProductRepo;
import com.marketplace.serviceproducts.repo.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final String feedbackURL = "http://service-feedbacks:8083/feedbacks/";

    public List<Product> fetchAll() {
        return productRepo.findAll();
    }

    public Product fetchByID(long id) throws IllegalArgumentException {
        final Optional<Product> product = productRepo.findById(id);

        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        } else {
            return product.get();
        }
    }

    public List<FeedbackDTO> getFeedbacksByProductId(long id) {
        final Optional<Product> maybeProduct = productRepo.findById(id);
        if (maybeProduct.isEmpty()) throw new IllegalArgumentException("Product not found");
        final Product product = maybeProduct.get();

        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<FeedbackDTO>> response = restTemplate.exchange(feedbackURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<FeedbackDTO>>() {});

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException("No feedback yet");
        }

        List<FeedbackDTO> productFeedbacks = new ArrayList<FeedbackDTO>();
        for (FeedbackDTO feedbackDTO : Objects.requireNonNull(response.getBody())) {
            if (feedbackDTO.getProduct() == product.getId()) {
                productFeedbacks.add(feedbackDTO);
            }
        }
        return productFeedbacks;
    }

    public Double getRatingByProductId(long id) {
        final Optional<Product> maybeProduct = productRepo.findById(id);
        if (maybeProduct.isEmpty()) throw new IllegalArgumentException("Product not found");
        final Product product = maybeProduct.get();

        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<FeedbackDTO>> response = restTemplate.exchange(feedbackURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<FeedbackDTO>>() {});

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException("No feedback yet");
        }

        List<FeedbackDTO> productFeedbacks = new ArrayList<FeedbackDTO>();
        for (FeedbackDTO feedbackDTO : Objects.requireNonNull(response.getBody())) {
            if (feedbackDTO.getProduct() == product.getId()) {
                productFeedbacks.add(feedbackDTO);
            }
        }

        Double rating =  productFeedbacks.stream()
                .mapToDouble(FeedbackDTO::getRating)
                .average()
                .orElse(Double.NaN);
        return Math.round(rating * 100D) / 100D;
    }

    public long create(long store, String name, BigDecimal price, boolean discontinued, String description) {
        final Product product = new Product(store, name, price, discontinued, description);
        final Product savedProduct = productRepo.save(product);

        return savedProduct.getId();
    }

    public void update(long id, long store, String name, BigDecimal price, boolean discontinued, String description) throws IllegalArgumentException {
        final Optional<Product> maybeProduct = productRepo.findById(id);
        if (maybeProduct.isEmpty()) throw new IllegalArgumentException("Product not found");

        final Product product = maybeProduct.get();
        if (store > 0) product.setStore(store);
        if (name != null && !name.isBlank()) product.setName(name);
        if (price.compareTo(BigDecimal.ZERO) > 0) product.setPrice(price);
        if (discontinued) product.setDiscontinued(discontinued);
        if (description != null && !description.isBlank()) product.setDescription(description);
        productRepo.save(product);
    }

    public void delete(long id) {
        productRepo.deleteById(id);
    }
}
