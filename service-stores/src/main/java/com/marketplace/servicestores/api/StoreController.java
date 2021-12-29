package com.marketplace.servicestores.api;

import com.marketplace.servicestores.api.dto.ProductDTO;
import com.marketplace.servicestores.api.dto.StoreDTO;
import com.marketplace.servicestores.repo.model.Store;
import com.marketplace.servicestores.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public final class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<List<Store>> index() {
        final List<Store> store = storeService.fetchAll();

        return ResponseEntity.ok(store);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> show(@PathVariable long id) {
        try {
            final Store store = storeService.fetchByID(id);

            return ResponseEntity.ok(store);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody StoreDTO store) {
        final String name = store.getName();
        final Date registration = store.getRegistration();
        final long id = storeService.create(name, registration);
        final String location = String.format("/store/%d", id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody StoreDTO store) {
        final String name = store.getName();

        try {
            storeService.update(id, name);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        storeService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDTO>> showStoreProducts(@PathVariable long id) {
        try {
            final List<ProductDTO> productDTO;
            productDTO = storeService.getProductsByStoreId(id);

            return ResponseEntity.ok(productDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
