package com.humansreadcode.example.repository;

import com.humansreadcode.example.config.AsyncConfiguration;
import com.humansreadcode.example.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface UserRepository extends MongoRepository<User, String> {

    @Async(AsyncConfiguration.TASK_EXECUTOR_REPOSITORY)
    CompletableFuture<Page<User>> readAllBy(final Pageable pageable);

    @Async(AsyncConfiguration.TASK_EXECUTOR_REPOSITORY)
    CompletableFuture<User> readOneById(final String id);
}
