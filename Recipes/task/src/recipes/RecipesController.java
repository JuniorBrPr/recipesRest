package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.models.DTOs;
import recipes.models.Recipe;

import javax.validation.Valid;

@RestController
public class RecipesController {
    private final RecipesRepository recipesRepository;

    public RecipesController(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }


    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable long id) {
        Recipe recipe = recipesRepository.findById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("/api/recipe/new")
    public DTOs.RecipeIdDTO postRecipe(@Valid @RequestBody Recipe recipe2) {
        if (recipe2 == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe not saved");
        }
        long id = recipesRepository.save(recipe2).getId();

        if (id != 0) {
            return new DTOs.RecipeIdDTO(id);
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
        recipesRepository.delete(recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
