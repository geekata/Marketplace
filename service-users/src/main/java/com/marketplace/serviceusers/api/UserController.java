package com.marketplace.serviceusers.api;

import com.marketplace.serviceusers.api.dto.FeedbackDTO;
import com.marketplace.serviceusers.api.dto.UserDTO;
import com.marketplace.serviceusers.repo.model.User;
import com.marketplace.serviceusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public final class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> index() {
        final List<User> users = userService.fetchAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable long id) {
        try {
            final User user = userService.fetchByID(id);

            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserDTO user) {
        final String firstname = user.getFirstname();
        final String lastname = user.getLastname();
        final String email = user.getEmail();
        final String password = user.getPassword();
        final long id = userService.create(firstname, lastname, email, password);
        final String location = String.format("/user/%d", id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody UserDTO user) {
        final String firstname = user.getFirstname();
        final String lastname = user.getLastname();
        final String email = user.getEmail();
        final String password = user.getPassword();

        try {
            userService.update(id, firstname, lastname, email, password);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/feedbacks")
    public ResponseEntity<List<FeedbackDTO>> showUserFeedbacks(@PathVariable long id) {
        try {
            final List<FeedbackDTO> feedbackDTO;
            feedbackDTO = userService.getFeedbacksByUserId(id);

            return ResponseEntity.ok(feedbackDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
