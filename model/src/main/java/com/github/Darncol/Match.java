package com.github.Darncol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Matches")
public class Match implements Comparable<Match> {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false)
    @JsonProperty
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false)
    @JsonProperty
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    @JsonProperty
    private Player winner;

    public Match(Player player1, Player player2, Player winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @JsonIgnore
    public PlayerDTO getPlayer1DTO() {
        return new PlayerDTO(player1);
    }

    @JsonIgnore
    public PlayerDTO getPlayer2DTO() {
        return new PlayerDTO(player2);
    }

    @Override
    public int compareTo(Match m) {
        return Long.compare(id, m.id);
    }
}
