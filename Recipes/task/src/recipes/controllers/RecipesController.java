package recipes.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.models.DTOs;
import recipes.models.Recipe;
import recipes.models.User;
import recipes.repos.RecipesRepository;
import recipes.repos.UsersRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class RecipesController {
    private final RecipesRepository recipesRepository;
    private final UsersRepository usersRepository;

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable long id) {
        Recipe recipe = recipesRepository.findById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> postRecipe(@Valid @RequestBody Recipe recipe) {
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe not saved");
        }
        Optional<User> user = usersRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        recipe.setUser(user.orElseThrow(() -> new UsernameNotFoundException(
                "Username not found: " + SecurityContextHolder.getContext().getAuthentication().getName())));

        recipe.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(1))));

        long id = recipesRepository.save(recipe).getId();

        if (id != 0) {
            return new ResponseEntity<>(new DTOs.idDTO(id), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe not saved");
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id) {
        Recipe recipe = recipesRepository.findById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }

        if (recipe.getUser() != null && !recipe.getUser().getEmail().equals(SecurityContextHolder.getContext()
                .getAuthentication().getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this recipe");
        }

        recipesRepository.delete(recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> putRecipe(@PathVariable long id, @Valid @RequestBody Recipe recipe) {
        Recipe recipe1 = recipesRepository.findById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe not saved");
        }
        if (recipe1 == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        if (recipe1.getUser() != null && !recipesRepository
                .findById(id).getUser().getEmail().equals(SecurityContextHolder.getContext()
                        .getAuthentication().getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to edit this recipe");
        }
        if (recipe.getUser() == null) {
            recipe.setUser(usersRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() ->
                            new UsernameNotFoundException(
                                    "Username not found: " +
                                            SecurityContextHolder.getContext().getAuthentication().getName())));
        }
        recipe.setId(id);
        recipe.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(1))));

        recipesRepository.save(recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/recipe/getAll")
    public ResponseEntity<?> getAllRecipes() {
        return new ResponseEntity<>(recipesRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<?> searchRecipe(@RequestParam(required = false) String category,
                                          @RequestParam(required = false) String name) {
        if (category == null && name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe not found");
        }

        Recipe recipe = null;

        if (category != null && name != null) {
            return new ResponseEntity<>(recipesRepository.findAllByNameContainingIgnoreCaseAndCategoryOrderByDateDesc(name, category), HttpStatus.OK);
        }
        if (category != null) {
            return new ResponseEntity<>(recipesRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category), HttpStatus.OK);
        }
        if (name != null) {
            return new ResponseEntity<>(recipesRepository.findAllByNameContainsIgnoreCaseOrderByDateDesc(name), HttpStatus.OK);
        }
        if (recipe != null) {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
