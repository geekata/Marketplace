package com.marketplace.servicefeedbacks.repo;

import com.marketplace.servicefeedbacks.repo.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
}
