package com.neneth.oskar_game.models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity(name = "categories")
@Getter
@Setter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<MovieItemEntity> movieItems;

    @ManyToOne
    @JoinColumn(name = "year_id", nullable = false)
    private YearEntity year;
}

