package com.marketplace.servicefeedbacks.api;

import com.marketplace.servicefeedbacks.api.dto.FeedbackDTO;
import com.marketplace.servicefeedbacks.repo.model.Feedback;
import com.marketplace.servicefeedbacks.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/feedbacks")
public final class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<Feedback>> index() {
        final List<Feedback> feedbacks = feedbackService.fetchAll();

        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> show(@PathVariable long id) {
        try {
            final Feedback feedback = feedbackService.fetchByID(id);

            return ResponseEntity.ok(feedback);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody FeedbackDTO feedback) {
        final long product = feedback.getProduct();
        final long userId = feedback.getUserId();
        final int rating = feedback.getRating();
        final Date date = feedback.getDate();
        final String text = feedback.getText();
        final long id = feedbackService.create(product, userId, rating, date, text);
        final String location = String.format("/feedback/%d", id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody FeedbackDTO feedback) {
        final int rating = feedback.getRating();
        final String text = feedback.getText();

        try {
            feedbackService.update(id, rating, text);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        feedbackService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
