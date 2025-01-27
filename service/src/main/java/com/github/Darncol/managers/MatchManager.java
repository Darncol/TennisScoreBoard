package com.github.Darncol.managers;

import com.github.Darncol.*;
import com.github.Darncol.services.FinishedMatchesPersistenceService;
import com.github.Darncol.services.OngoingMathcesService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MatchManager {
    public UUID startNewMatch(String name1, String name2) throws IOException, InterruptedException, ChatGPTException, InvalidDataException {
        return OngoingMathcesService.startNewMatch(name1, name2);
    }

    public Match getMatch(UUID uuid) {
        return OngoingMathcesService.getMatch(uuid);
    }

    public void nextRound(String roundWinner, UUID uuid) {
        var matchCalculation = OngoingMathcesService.getCalculation(uuid);

        switch (roundWinner) {
            case "1":
                matchCalculation.addPointToPlayer1();
                break;
            case "2":
                matchCalculation.addPointToPlayer2();
                break;
            default:
                break;
        }
    }

    public boolean validateWinCondition(UUID uuid) throws DataBaseException {
        Match ongoingMatch = OngoingMathcesService.getMatch(uuid);
        var matchCalculation = OngoingMathcesService.getCalculation(uuid);

        if (matchCalculation.isGameFinished() && ongoingMatch.getWinner() != null) {
            FinishedMatchesPersistenceService.save(ongoingMatch);
            return true;
        }

        return false;
    }

    public String getWinnerName(UUID uuid) {
        Match ongoingMatch = OngoingMathcesService.getMatch(uuid);
        return ongoingMatch.getWinner().getName();
    }

    public PlayerDTO getPlayer1DTO(UUID uuid) {
        Match ongoingMatch = OngoingMathcesService.getMatch(uuid);
        var matchCalculation = OngoingMathcesService.getCalculation(uuid);

        return new PlayerDTO(ongoingMatch.getPlayer1(), matchCalculation.getPlayer1Score());
    }

    public PlayerDTO getPlayer2DTO(UUID uuid) {
        Match ongoingMatch = OngoingMathcesService.getMatch(uuid);
        var matchCalculation = OngoingMathcesService.getCalculation(uuid);

        return new PlayerDTO(ongoingMatch.getPlayer2(), matchCalculation.getPlayer2Score());
    }

    public void finishMatch(UUID uuid) {
        OngoingMathcesService.deleteFinishedMatch(uuid);
    }
}
