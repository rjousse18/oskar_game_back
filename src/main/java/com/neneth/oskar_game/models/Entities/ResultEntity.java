package com.neneth.oskar_game.models.Entities;

import com.neneth.oskar_game.models.Player;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity(name = "results")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultEntity {
    @Id
    private String roomId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "players", columnDefinition = "jsonb")
    private List<Player> players;

}
