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

    public void awardPointToFirstPlayer() {
        if (isTieBreakMode()) {
            new PlayerActionTieBreak(player1Score, player2Score).processPoint();
        } else {
            new PlayerAction(player1Score, player2Score).processPoint();
        }

        checkForMatchWinner(player1Score, match.getPlayer1());
    }

    public void awardPointToSecondPlayer() {
        if (isTieBreakMode()) {
            new PlayerActionTieBreak(player2Score, player1Score).processPoint();
        } else {
            new PlayerAction(player2Score, player1Score).processPoint();
        }

        checkForMatchWinner(player2Score, match.getPlayer2());
    }

    private void checkForMatchWinner(Score score, Player player) {
        if (score.isWinner()) {
            match.setWinner(player);
            gameFinished = true;
        }
    }

    private boolean isTieBreakMode() {
        return player1Score.getGames() == 6 && player2Score.getGames() == 6;
    }
}

@Getter
@AllArgsConstructor
class PlayerAction {
    private Score playerScore;
    private Score opponentScore;

    public void processPoint() {
        if (isDeuce()) {
            handleAdvantagePoint();
            return;
        }

        updateScore();

        if (playerScore.getGames() == 7) {
            awardSet(playerScore);
        }
    }

    protected void updateScore() {
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
                awardGame(playerScore);
                break;
        }
    }

    protected void awardSet(Score player) {
        player.setSets(player.getSets() + 1);
        resetPoints();
        resetGames();
    }

    private void handleAdvantagePoint() {
        if (!playerScore.getAdvantage() && !opponentScore.getAdvantage()) {
            playerScore.setAdvantage(true);
        } else if (!playerScore.getAdvantage() && opponentScore.getAdvantage()) {
            opponentScore.setAdvantage(false);
        } else if (playerScore.getAdvantage() && !opponentScore.getAdvantage()) {
            playerScore.setAdvantage(false);
            opponentScore.setAdvantage(false);
            awardGame(playerScore);
        }
    }

    private boolean isDeuce() {
        return playerScore.getPoints() == 40 && opponentScore.getPoints() == 40;
    }

    private void awardGame(Score player) {
        player.setGames(player.getGames() + 1);
        resetPoints();
    }

    private void resetPoints() {
        playerScore.setPoints(0);
        opponentScore.setPoints(0);
    }

    private void resetGames() {
        playerScore.setGames(0);
        opponentScore.setGames(0);
    }
}

class PlayerActionTieBreak extends PlayerAction {
    public PlayerActionTieBreak(Score playerScore, Score opponentScore) {
        super(playerScore, opponentScore);
    }

    @Override
    protected void updateScore() {
        Score player = getPlayerScore();
        player.setPoints(player.getPoints() + 1);

        if (player.getPoints() >= 7 && getPlayerScore().getPoints() - getOpponentScore().getPoints() >= 2) {
            awardSet(player);
        }
    }
}
