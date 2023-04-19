package recipes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    int id;
    String name;
    String description;
    String[] ingredients;
    String[] directions;
}
