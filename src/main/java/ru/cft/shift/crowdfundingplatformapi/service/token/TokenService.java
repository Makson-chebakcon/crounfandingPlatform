package ru.cft.shift.crowdfundingplatformapi.service.token;

import org.apache.commons.lang3.tuple.Pair;
import ru.cft.shift.crowdfundingplatformapi.dto.TokenData;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;

import java.util.Date;
import java.util.UUID;

public interface TokenService {

    String generateAccessToken(UUID id, String email, PersonRole role);

    Pair<String, Date> generateRefreshTokenAndExpiresDate(UUID id, String email, PersonRole role);

    TokenData decodeAccessToken(String token);

}
