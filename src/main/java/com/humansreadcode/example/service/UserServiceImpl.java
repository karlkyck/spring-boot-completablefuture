package com.humansreadcode.example.service;

import com.humansreadcode.example.domain.User;
import com.humansreadcode.example.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CompletableFuture<Page<User>> findAll(final Pageable pageable) {
        return userRepository.readAllBy(pageable);
    }

    @Override
    public CompletableFuture<User> findOneById(final String id) {
        return userRepository.readOneById(id);
    }
}
