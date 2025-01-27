package com.github.Darncol.services;

import com.github.Darncol.DatabaseManager;
import com.github.Darncol.Match;
import com.github.Darncol.Player;

import java.util.List;


public class FinishedMatchesPersistenceService {
    private final static FinishedMatchesPersistenceService INSTANCE = new FinishedMatchesPersistenceService();

    public static FinishedMatchesPersistenceService getInstance() {
        return INSTANCE;
    }

    private FinishedMatchesPersistenceService() {
    }

    public static List<Match> getAllMatches() throws IllegalArgumentException {
        return DatabaseManager.getEntities(Match.class);
    }

    public static void save(Match match) throws IllegalArgumentException {
        DatabaseManager.saveEntity(match);
    }

    public static void save(Player player) throws IllegalArgumentException {
        if(DatabaseManager.getPlayerByName(player.getName()) == null) {
            DatabaseManager.saveEntity(player);
        }
    }
}
