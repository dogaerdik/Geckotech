package com.gecotech.models.entity;

import com.gecotech.models.enums.TypeOfMeal;
import com.gecotech.models.entity.base.AuditEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Recipe extends AuditEntity<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name= "recipe_id", referencedColumnName = "id")
    private List<Ingredient> ingredients = new ArrayList<>();
    private String timeRequired;
    private TypeOfMeal typeOfMeal;
}