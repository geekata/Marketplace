package com.marketplace.serviceproducts.repo;

import com.marketplace.serviceproducts.api.dto.FeedbackDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.marketplace.serviceproducts.repo.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
}
