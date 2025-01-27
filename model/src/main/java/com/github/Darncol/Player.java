package com.github.Darncol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "Players")
public class Player {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Getter
    @Column(nullable = false, unique = true)
    @JsonProperty
    private String name;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }
}
