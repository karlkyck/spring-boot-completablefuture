package com.humansreadcode.example.repository;

import com.humansreadcode.example.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.concurrent.CompletableFuture;

public interface UserRepository extends MongoRepository<User, String> {

    CompletableFuture<Page<User>> readAllBy(final Pageable pageable);

    CompletableFuture<User> readOneById(final String id);
}
