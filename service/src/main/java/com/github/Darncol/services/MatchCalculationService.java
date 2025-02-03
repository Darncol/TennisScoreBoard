package com.github.Darncol.services;

import com.github.Darncol.Match;
import com.github.Darncol.Player;
import com.github.Darncol.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MatchCalculationService {
    @Getter
    private Match match;

    @Getter
    private boolean gameFinished = false;

    @Getter
    private Score player1Score = new Score();
    @Getter
    private Score player2Score = new Score();

    public MatchCalculationService(Match match) {
        this.match = match;
    }

    public void addPointToPlayer1() {
        if (isTieBreakActive()) {
            new PlayerActionTieBreak(player1Score, player2Score).execute();
        } else {
            new PlayerAction(player1Score, player2Score).execute();
        }

        evaluateWinCondition(player1Score, match.getPlayer1());
    }

    public void addPointToPlayer2() {
        if (isTieBreakActive()) {
            new PlayerActionTieBreak(player2Score, player1Score).execute();
        } else {
            new PlayerAction(player2Score, player1Score).execute();
        }

        evaluateWinCondition(player2Score, match.getPlayer2());
    }

    private void evaluateWinCondition(Score score, Player player) {
        if (score.isWinner()) {
            match.setWinner(player);
            gameFinished = true;
        }
    }

    private boolean isTieBreakActive() {
        return player1Score.getGames() == 6 && player2Score.getGames() == 6;
    }
}

@Getter
@AllArgsConstructor
class PlayerAction {
    private Score playerScore;
    private Score opponentScore;

    public void execute() {
        if (isAdvantageConditionMet()) {
            incrementAdvantage();
            return;
        }

        incrementScore();

        if (playerScore.getGames() == 7) {
            incrementSets(playerScore);
        }
    }

    protected void incrementScore() {
        switch (playerScore.getPoints()) {
            case 0:
                playerScore.setPoints(15);
                break;
            case 15:
                playerScore.setPoints(30);
                break;
            case 30:
                playerScore.setPoints(40);
                break;
            default:
                incrementGames(playerScore);
                break;
        }
    }

    private void incrementAdvantage() {
        if (!playerScore.getAdvantage() && !opponentScore.getAdvantage()) {
            playerScore.setAdvantage(true);
        } else if (!playerScore.getAdvantage() && opponentScore.getAdvantage()) {
            opponentScore.setAdvantage(false);
        } else if (playerScore.getAdvantage() && !opponentScore.getAdvantage()) {
            playerScore.setAdvantage(false);
            opponentScore.setAdvantage(false);
            incrementGames(playerScore);
        }
    }

    private boolean isAdvantageConditionMet() {
        return playerScore.getPoints() == 40 && opponentScore.getPoints() == 40;
    }

    private void incrementGames(Score player) {
        player.setGames(player.getGames() + 1);
        resetScore();
    }

    protected void incrementSets(Score player) {
        player.setSets(player.getSets() + 1);
        resetScore();
        resetGamesCount();
    }

    private void resetScore() {
        playerScore.setPoints(0);
        opponentScore.setPoints(0);
    }

    private void resetGamesCount() {
        playerScore.setGames(0);
        opponentScore.setGames(0);
    }
}

class PlayerActionTieBreak extends PlayerAction {
    public PlayerActionTieBreak(Score playerScore, Score opponentScore) {
        super(playerScore, opponentScore);
    }

    @Override
    protected void incrementScore() {
        Score player = getPlayerScore();
        player.setPoints(player.getPoints() + 1);

        if (player.getPoints() >= 7 && getPlayerScore().getPoints() - getOpponentScore().getPoints() >= 2) {
            incrementSets(player);
        }
    }
}
