package recipes.models;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class RecipeRepository {
    private static int id = 0;
    private final ConcurrentHashMap<Integer, Recipe> recipes = new ConcurrentHashMap<>();

    public int addRecipe(Recipe recipe) {
        recipe.setId(id++);
        recipes.put(recipe.getId(), recipe);
        return recipe.getId();
    }

    public Recipe getRecipe(int id) {
        return recipes.get(id);
    }
}
