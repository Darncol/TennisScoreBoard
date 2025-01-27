package com.github.Darncol;

import lombok.Getter;

@Getter
public class PlayerDTO {
    private String name;
    private int points = 0;
    private int games = 0;
    private int sets = 0;

    public PlayerDTO(Player player, Score score) {
        this.name = player.getName();
        this.points = score.getPoints();
        this.games = score.getGames();
        this.sets = score.getSets();
    }

    public PlayerDTO(Player player) {
        this.name = player.getName();
    }

    public PlayerDTO(String name) {
        this.name = name;
    }
}
