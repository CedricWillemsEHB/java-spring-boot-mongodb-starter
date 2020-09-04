package com.mongodb.starter.repositories;

import com.mongodb.starter.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    User save(User user);

    List<User> saveAll(List<User> users);

    List<User> findAll();

    List<User> findAll(List<String> ids);

    User findById(String id);

    User findByEmail(String email);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    User update(User user);

    long update(List<User> users);
}
