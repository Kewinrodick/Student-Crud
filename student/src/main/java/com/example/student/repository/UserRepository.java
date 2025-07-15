package com.example.student.repository;

import com.example.student.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findByName(String name);
    public boolean existsByEmail(String email);
}
