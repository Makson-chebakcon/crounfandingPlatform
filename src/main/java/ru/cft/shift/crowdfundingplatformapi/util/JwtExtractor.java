package ru.cft.shift.crowdfundingplatformapi.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import ru.cft.shift.crowdfundingplatformapi.security.TokenData;

import java.util.UUID;

@UtilityClass
public class JwtExtractor {

    public static UUID extractId(Authentication authentication) {
        return extractTokenData(authentication).getId();
    }

    private static TokenData extractTokenData(Authentication authentication) {
        return (TokenData) authentication.getPrincipal();
    }

}
