package com.gecotech.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
public class Ingredient{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String amount;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @JsonIgnore
    public Recipe getRecipe()
    {
        return recipe;
    }
}
