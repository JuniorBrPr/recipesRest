package recipes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @JsonIgnore
    int id;
    String name;
    String description;
    String[] ingredients;
    String[] directions;
}
