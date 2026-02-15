package com.neneth.oskar_game.models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "movie_items")
@Getter
@Setter
public class MovieItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieItemId;

    @Column
    private String nominee;

    @Column
    private Boolean won;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;
}

