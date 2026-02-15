package com.neneth.oskar_game.models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity(name = "movies")
@Getter
@Setter
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column
    private String original_title;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "year_id", nullable = false)
    private YearEntity year;

    @OneToMany(mappedBy="movie")
    private Set<MovieItemEntity> movieItems;
}
