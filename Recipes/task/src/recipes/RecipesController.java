package recipes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.models.Recipe;

@RestController
public class RecipesController {
    private final Recipe recipe = new Recipe();

    @GetMapping("/api/recipe")
    public Recipe getRecipe() {
        return recipe;
    }

    @PostMapping("/api/recipe")
    public void postRecipe(@RequestBody Recipe recipe2) {
        recipe.setName(recipe2.getName());
        recipe.setDescription(recipe2.getDescription());
        recipe.setIngredients(recipe2.getIngredients());
        recipe.setDirections(recipe2.getDirections());
    }
}
