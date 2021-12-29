package com.marketplace.serviceusers.service;

import com.marketplace.serviceusers.api.dto.FeedbackDTO;
import com.marketplace.serviceusers.repo.UserRepo;
import com.marketplace.serviceusers.repo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class UserService {

    private final UserRepo userRepo;
    private final String feedbackURL = "http://service-feedbacks:8083/feedbacks/";

    public List<User> fetchAll() {
        return userRepo.findAll();
    }

    public User fetchByID(long id) throws IllegalArgumentException {
        final Optional<User> user = userRepo.findById(id);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        } else {
            return user.get();
        }
    }

    public List<FeedbackDTO> getFeedbacksByUserId(long id) {
        final Optional<User> maybeUser = userRepo.findById(id);
        if (maybeUser.isEmpty()) throw new IllegalArgumentException("Product not found");
        final User user = maybeUser.get();

        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<FeedbackDTO>> response = restTemplate.exchange(feedbackURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<FeedbackDTO>>() {});

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException("No feedback yet");
        }

        List<FeedbackDTO> userFeedbacks = new ArrayList<FeedbackDTO>();
        for (FeedbackDTO feedbackDTO : Objects.requireNonNull(response.getBody())) {
            if (feedbackDTO.getUserId() == user.getId()) {
                userFeedbacks.add(feedbackDTO);
            }
        }
        return userFeedbacks;
    }

    public long create(String firstname, String lastname, String email, String password) {
        final User user = new User(firstname, lastname, email, password);
        final User savedUser = userRepo.save(user);

        return savedUser.getId();
    }

    public void update(long id, String firstname, String lastname, String email, String password) throws IllegalArgumentException {
        final Optional<User> maybeUser = userRepo.findById(id);
        if (maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");

        final User user = maybeUser.get();
        if (firstname != null && !firstname.isBlank()) user.setFirstname(firstname);
        if (lastname != null && !lastname.isBlank()) user.setLastname(lastname);
        if (email != null && !email.isBlank()) user.setEmail(email);
        if (password != null && !password.isBlank()) user.setPassword(password);
        userRepo.save(user);
    }

    public void delete(long id) {
        userRepo.deleteById(id);
        }
}

