package com.github.Darncol.services;

import com.github.Darncol.DataBaseException;
import com.github.Darncol.DatabaseManager;
import com.github.Darncol.Match;

import java.util.List;


public class FinishedMatchesPersistenceService {
    private final static FinishedMatchesPersistenceService INSTANCE = new FinishedMatchesPersistenceService();

    public static FinishedMatchesPersistenceService getInstance() {
        return INSTANCE;
    }

    private FinishedMatchesPersistenceService() {
    }

    public static void save(Match match) throws DataBaseException {
        DatabaseManager.saveEntity(match);
    }

    public static List<Match> getAllMatchesInPage(String page) {
        return DatabaseManager.getMathesInPage(page);
    }

    public static List<Match> getMatchesWithPlayer(String name, String page) {
        return DatabaseManager.getMatchesWithPlayer(name, page);
    }
}
