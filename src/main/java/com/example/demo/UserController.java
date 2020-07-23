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
    public Iterable<User> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    public User create(@RequestBody User user) {
        return this.repository.save(user);
    }

    @GetMapping("/{id}")
    public Optional<User> find(@PathVariable long id) {
        return this.repository.findById(id);
    }

    @PatchMapping("/{id}")
    public User patch(@PathVariable Long id, @RequestBody User updates) {
        Optional<User> current = this.repository.findById(id);
        if (updates.getEmail() != null) {
            current.get().setEmail(updates.getEmail());
        }
        if (updates.getPassword() != null) {
            current.get().setPassword(updates.getPassword());
        }
        return this.repository.save(current.get());
    }

    @DeleteMapping("/{id}")
    public Count delete(@PathVariable long id) {
        this.repository.deleteById(id);
        Count count = new Count();

        count.setCount(this.repository.count());
        return count;
    }

    @PostMapping("/authenticate")
    public Authentication auth(@RequestBody User user){
        Authentication auth = new Authentication();
        Authentication.AuthUser authUser = new Authentication.AuthUser();

        User login = this.repository.findByEmail(user.getEmail());


        if(login != null) {
            if (login.getPassword().equals(user.getPassword())) {
                auth.setAuthenticated(true);
                authUser.setEmail(user.getEmail());
                authUser.setId(login.getId());
                auth.setUser(authUser);
            } else {
                auth.setAuthenticated(false);
            }
        }


        return auth;

    }
}
