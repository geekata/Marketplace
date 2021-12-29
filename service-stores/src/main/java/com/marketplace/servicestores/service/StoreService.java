package com.marketplace.servicestores.service;
import com.marketplace.servicestores.api.dto.ProductDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marketplace.servicestores.repo.StoreRepo;
import com.marketplace.servicestores.repo.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequiredArgsConstructor
@Service
public final class StoreService {

    private final StoreRepo storeRepo;
    private final String productURL = "http://service-products:8082/products/";

    public List<Store> fetchAll() {
        return storeRepo.findAll();
    }

    public Store fetchByID(long id) throws IllegalArgumentException {
        final Optional<Store> store = storeRepo.findById(id);

        if (store.isEmpty()) {
            throw new IllegalArgumentException("Store not found");
        } else {
            return store.get();
        }
    }

    public List<ProductDTO> getProductsByStoreId(long id) {
        final Optional<Store> maybeStore = storeRepo.findById(id);
        if (maybeStore.isEmpty()) throw new IllegalArgumentException("Store not found");
        final Store store = maybeStore.get();

        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(productURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ProductDTO>>() {});

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException("Product not found");
        }

        List<ProductDTO> storeProducts = new ArrayList<ProductDTO>();
        for (ProductDTO productDTO : Objects.requireNonNull(response.getBody())) {
            if (productDTO.getStore() == store.getId()) {
                storeProducts.add(productDTO);
            }
        }
        return storeProducts;
    }

    public long create(String name, Date registration) {
        final Store store = new Store(name, registration);
        final Store savedStore = storeRepo.save(store);

        return savedStore.getId();
    }

    public void update(long id, String name) throws IllegalArgumentException {
        final Optional<Store> maybeStore = storeRepo.findById(id);
        if (maybeStore.isEmpty()) throw new IllegalArgumentException("Store not found");

        final Store store = maybeStore.get();
        if (name != null && !name.isBlank()) store.setName(name);
        storeRepo.save(store);
    }

    public void delete(long id) {
        storeRepo.deleteById(id);
    }
}
