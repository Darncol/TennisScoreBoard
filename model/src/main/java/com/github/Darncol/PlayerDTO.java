package com.github.Darncol;

import lombok.Getter;

@Getter
public class PlayerDTO {
    private String name;
    private String points = "0";
    private int games = 0;
    private int sets = 0;
    private boolean advantage;

    public PlayerDTO(Player player, Score score) {
        this.name = player.getName();
        this.points = String.valueOf(score.getPoints());
        this.games = score.getGames();
        this.sets = score.getSets();
        this.advantage = score.getAdvantage();
    }

    public PlayerDTO(Player player) {
        this.name = player.getName();
    }

    public String getPoints() {
        return advantage ?
                "AD" :
                points;
    }
}