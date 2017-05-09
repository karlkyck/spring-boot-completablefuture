package com.humansreadcode.example.web;

import com.humansreadcode.example.domain.User;
import com.humansreadcode.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RestController
@RequestMapping(value = UserController.REQUEST_PATH_API_USERS)
public class UserController {

    static final String REQUEST_PATH_API_USERS = "/api/users";
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final String REQUEST_PATH_API_USERS_INDIVIDUAL_USER = "/{userId}";

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Async
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletableFuture<ResponseEntity> getUsers(final Pageable paging) {
        return userService
                .findAll(paging)
                .<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleGetUsersFailure);
    }

    @Async
    @GetMapping(value = REQUEST_PATH_API_USERS_INDIVIDUAL_USER,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletableFuture<ResponseEntity> getUser(@PathVariable final String userId) {
        return userService
                .findOneById(userId)
                .thenApply(Optional::ofNullable)
                .thenApply(mapMaybeUserToResponse)
                .exceptionally(handleGetUserFailure.apply(userId));
    }

    private static Function<Throwable, ResponseEntity> handleGetUsersFailure = throwable -> {
        log.error("Unable to retrieve users", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };

    private static Function<Optional<User>, ResponseEntity> mapMaybeUserToResponse = maybeUser -> maybeUser
            .<ResponseEntity>map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());

    private static Function<String, Function<Throwable, ResponseEntity>> handleGetUserFailure = userId -> throwable -> {
        log.error(String.format("Unable to retrieve user for id: %s", userId), throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };
}
