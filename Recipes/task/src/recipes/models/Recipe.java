package recipes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    Long id;

    @NotBlank
    String name;

    @NotBlank
    String description;

    @NotBlank
    String category;

    @Temporal(TemporalType.TIMESTAMP)
    Date date;

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "INGREDIENTS")
    @JoinColumn(nullable = false)
    private List<String> ingredients = new ArrayList<>();

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "DIRECTIONS")
    @JoinColumn(nullable = false)
    private List<String> directions = new ArrayList<>();
}
