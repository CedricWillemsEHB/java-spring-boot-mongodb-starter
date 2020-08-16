package com.mongodb.starter.controllers;

import com.mongodb.starter.models.User;
import com.mongodb.starter.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class UserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("users")
    @ResponseStatus(HttpStatus.CREATED)
    public List<User> postUsers(@RequestBody List<User> users) {
        return userRepository.saveAll(users);
    }

    @GetMapping("users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/user", params="emailID", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmails(@RequestParam("emailID") String email) {
        System.out.println("getUserByEmails");
        User user = userRepository.findByEmail(email);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/user", params="userID", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByID(@RequestParam("userID") String id) {
        User user = userRepository.findById(id);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("users/{ids}")
    public List<User> getUsers(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return userRepository.findAll(listIds);
    }

    @GetMapping("users/count")
    public Long getCount() {
        return userRepository.count();
    }

    @DeleteMapping("user/{id}")
    public Long deleteUser(@PathVariable String id) {
        return userRepository.delete(id);
    }

    @DeleteMapping("users/{ids}")
    public Long deleteUsers(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return userRepository.delete(listIds);
    }

    @DeleteMapping("users")
    public Long deleteUsers() {
        return userRepository.deleteAll();
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public User putUser(@RequestBody User user) {
        System.out.println("putUser");
        return userRepository.update(user);
    }

    @PutMapping("users")
    public Long putUser(@RequestBody List<User> users) {
        return userRepository.update(users);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

}
