package com.neneth.oskar_game.models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity(name="years")
@Getter
@Setter
public class YearEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long yearId;

    @Column
    private Integer year;

    @Column
    private String oscar_ceremony;

    @OneToMany(mappedBy="year")
    private Set<CategoryEntity> categories;
}
