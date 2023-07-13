package ru.cft.shift.crowdfundingplatformapi.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CodeGenerator {

    private static final int MIN_LETTER = 65;
    private static final int MAX_LETTER = 91;
    private static final int LETTER_REGISTER_SHIFT = 32;
    private static final int MIN_DIGIT = 49;
    private static final int MAX_DIGIT = 58;

    private final Random random;

    public String generateCode(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomInt = random.nextInt(MIN_LETTER, MAX_LETTER);

            randomInt = random.nextBoolean() ? randomInt : randomInt + LETTER_REGISTER_SHIFT;
            randomInt = random.nextBoolean() ? randomInt : random.nextInt(MIN_DIGIT, MAX_DIGIT);

            stringBuilder.append((char) randomInt);
        }

        return stringBuilder.toString();
    }

}
