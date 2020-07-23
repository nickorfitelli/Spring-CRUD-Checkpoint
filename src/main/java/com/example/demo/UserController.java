package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<User>all(){return this.repository.findAll();}

    @PostMapping("")
    public User create(@RequestBody User user) {
        return this.repository.save(user);
    }

    @GetMapping("/{id}")
    public Optional<User> find(@PathVariable long id ) {
        return this.repository.findById(id);
    }
}
