package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.models.DTOs;
import recipes.models.Recipe;
import recipes.models.RecipeRepository;

@RestController
public class RecipesController {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipesController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable int id) {
        Recipe recipe = recipeRepository.getRecipe(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("/api/recipe/new")
    public DTOs.RecipeIdDTO postRecipe(@RequestBody Recipe recipe2) {
        return new DTOs.RecipeIdDTO(recipeRepository.addRecipe(recipe2));
    }
}
