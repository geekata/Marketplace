package com.marketplace.servicefeedbacks.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marketplace.servicefeedbacks.repo.FeedbackRepo;
import com.marketplace.servicefeedbacks.repo.model.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class FeedbackService {

    private final FeedbackRepo feedbackRepo;

    public List<Feedback> fetchAll() {
        return feedbackRepo.findAll();
    }

    public Feedback fetchByID(long id) throws IllegalArgumentException {
        final Optional<Feedback> user = feedbackRepo.findById(id);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("Feedback not found");
        } else {
            return user.get();
        }
    }

    public long create(long product, long user, int rating, Date date, String text) {
        final Feedback feedback = new Feedback(product, user, rating, date, text);
        final Feedback savedFeedback = feedbackRepo.save(feedback);

        return savedFeedback.getId();
    }

    public void update(long id, int rating, String text) throws IllegalArgumentException {
        final Optional<Feedback> maybeFeedback = feedbackRepo.findById(id);
        if (maybeFeedback.isEmpty()) throw new IllegalArgumentException("Feedback not found");

        final Feedback feedback = maybeFeedback.get();
        if (rating > 0 && rating < 6) feedback.setRating(rating);
        if (text != null && !text.isBlank()) feedback.setText(text);
        feedbackRepo.save(feedback);
    }

    public void delete(long id) {
        feedbackRepo.deleteById(id);
    }
}
