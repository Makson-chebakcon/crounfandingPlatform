package ru.cft.shift.crowdfundingplatformapi.service;

import org.apache.commons.lang3.tuple.Pair;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.security.TokenData;

import java.util.Date;

public interface TokenService {

    String generateAccessToken(Person person);

    Pair<String, Date> generateRefreshTokenAndExpiresDate(Person person);

    TokenData decodeAccessToken(String token);

}
