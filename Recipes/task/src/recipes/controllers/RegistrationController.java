package recipes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.repos.UsersRepository;
import recipes.models.User;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class RegistrationController {
    private final UsersRepository userRepo;
    private final PasswordEncoder encoder;

    @GetMapping("/api/register")
    public Iterable<User> register() {
        return userRepo.findAll();
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            return ResponseEntity.badRequest().body("Invalid email");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepo.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}