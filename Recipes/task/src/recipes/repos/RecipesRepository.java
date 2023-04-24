package recipes.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import recipes.models.Recipe;

import java.util.List;

@Service
public interface RecipesRepository extends CrudRepository<Recipe, Integer> {
    Recipe findById(long id);
    List<Recipe> findAllByNameContainsIgnoreCaseOrderByDateDesc(String name);
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameContainingIgnoreCaseAndCategoryOrderByDateDesc(String name, String category);

}
