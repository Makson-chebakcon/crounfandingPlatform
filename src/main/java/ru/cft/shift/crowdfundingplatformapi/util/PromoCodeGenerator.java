package ru.cft.shift.crowdfundingplatformapi.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class PromoCodeGenerator {
    private static final int PROMO_KODE_LEHGTH = 8;
    private final Random random;
    private static final int MIN_LETTER = 65;
    private static final int MAX_LETTER = 91;
    private static final int LETTER_REGISTER_SHIFT = 32;
    private static final int MIN_DIGIT = 49;
    private static final int MAX_DIGIT = 58;

    public String generateCode(){
        StringBuilder stringBuilder = new StringBuilder(PROMO_KODE_LEHGTH);
        for (int i = 0; i < PROMO_KODE_LEHGTH; i++) {
            int randomInt = random.nextInt(MIN_LETTER, MAX_LETTER);

            randomInt = random.nextBoolean() ? randomInt : randomInt + LETTER_REGISTER_SHIFT;
            randomInt = random.nextBoolean() ? randomInt : random.nextInt(MIN_DIGIT, MAX_DIGIT);

            stringBuilder.append((char) randomInt);
        }

        return stringBuilder.toString();
    }
}
