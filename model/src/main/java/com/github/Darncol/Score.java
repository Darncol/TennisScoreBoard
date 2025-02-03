package com.github.Darncol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Score {
    private int points = 0;
    private int games = 0;
    private int sets = 0;
    private Boolean advantage = false;

    public boolean isWinner() {
        return sets >= 2;
    }
}
