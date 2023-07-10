package ru.cft.shift.crowdfundingplatformapi.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class PasswordGenerator {

    private final Random random;
    private static final int PASSWORD_LENGTH = 16;
    private static final int MIN_LETTER = 65;
    private static final int MAX_LETTER = 91;
    private static final int LETTER_REGISTER_SHIFT = 32;
    private static final int MIN_DIGIT = 49;
    private static final int MAX_DIGIT = 58;

    public String generatePassword() {
        StringBuilder stringBuilder = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomInt = random.nextInt(MIN_LETTER, MAX_LETTER);

            randomInt = random.nextBoolean() ? randomInt : randomInt + LETTER_REGISTER_SHIFT;
            randomInt = random.nextBoolean() ? randomInt : random.nextInt(MIN_DIGIT, MAX_DIGIT);

            stringBuilder.append((char) randomInt);
        }

        return stringBuilder.toString();
    }

}
