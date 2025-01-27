package com.github.Darncol.services;

import com.github.Darncol.*;
import com.github.Darncol.managers.ValidationManager;
import lombok.Getter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMathcesService {
    @Getter
    private static final OngoingMathcesService instance = new OngoingMathcesService();

    private static final Map<UUID, Match> onGoingMatch = new ConcurrentHashMap<>();
    private static final Map<UUID, MatchCalculationService> onGoingMatchCalculation = new ConcurrentHashMap<>();

    private OngoingMathcesService() {}

    public static UUID startNewMatch(String name1, String name2) throws IOException, ChatGPTException, InterruptedException, InvalidDataException {
        ValidationManager.sameNameValidation(name1, name2);
        ValidationManager.nameValidation(name1);
        ValidationManager.nameValidation(name2);

        Player player1 = isPlayerExist(name1) ?
                getPlayer(name1) :
                save(name1);

        Player player2 = isPlayerExist(name2) ?
                getPlayer(name2) :
                save(name2);

        UUID uuid = UUID.randomUUID();

        Match match = new Match(player1, player2);
        var matchCalculation = new MatchCalculationService(match);

        onGoingMatchCalculation.put(uuid, matchCalculation);
        onGoingMatch.put(uuid, match);

        return uuid;
    }

    public static Match getMatch(UUID uuid) {
        return onGoingMatch.get(uuid);
    }

    public static MatchCalculationService getCalculation(UUID uuid) {
        return onGoingMatchCalculation.get(uuid);
    }

    public static void deleteFinishedMatch(UUID uuid) {
        onGoingMatch.remove(uuid);
        onGoingMatchCalculation.remove(uuid);
    }

    private static Player save(String name) throws DataBaseException {
        Player player = new Player(name);
        DatabaseManager.saveEntity(player);
        return player;
    }

    private static boolean isPlayerExist(String playerName) throws DataBaseException {
        return DatabaseManager.getPlayerByName(playerName) != null;
    }

    private static Player getPlayer(String playerName) throws DataBaseException {
        return DatabaseManager.getPlayerByName(playerName);
    }
}
