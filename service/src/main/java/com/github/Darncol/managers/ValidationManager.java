package com.github.Darncol.managers;

import com.github.Darncol.ChatGPTException;
import com.github.Darncol.DatabaseManager;
import com.github.Darncol.InvalidDataException;
import com.github.Darncol.hidden.HiddenApi;
import com.github.Darncol.services.ChatGPTApiService;

import java.io.IOException;

public class ValidationManager {
    private final static String API_KEY = HiddenApi.KEY;
    private final static ChatGPTApiService gptService = new ChatGPTApiService(API_KEY);

    public static void sameNameValidation(String name1, String name2) {
        if (name1.equals(name2)) {
            throw new InvalidDataException("The same name is not allowed");
        }
    }

    public static void nameValidation(String name) throws InvalidDataException, IOException, ChatGPTException, InterruptedException {
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("Please enter both player names");
        } else if (name.length() < 3) {
            throw new InvalidDataException("name length must be at least 3 characters");
        } else if (name.length() >= 30) {
            throw new InvalidDataException("maximum length of name is 30 characters");
        } else if (!name.matches("^[a-zA-Zа-яА-ЯёЁ]+( [a-zA-Zа-яА-ЯёЁ]+)*$")) {
            throw new InvalidDataException("name contains invalid characters");
        } else if(gptService.checkForInappropriateContent(name)) {
            throw new InvalidDataException("Cursing and cracking dirty jokes are strictly off-limits ;)");
        }
    }
}
