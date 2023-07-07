package ru.cft.shift.crowdfundingplatformapi.service.token;

import org.apache.commons.lang3.tuple.Pair;
import ru.cft.shift.crowdfundingplatformapi.dto.TokenData;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;

import java.util.Date;

public interface TokenService {

    String generateAccessToken(Person person);

    Pair<String, Date> generateRefreshTokenAndExpiresDate(Person person);

    TokenData decodeAccessToken(String token);

}
