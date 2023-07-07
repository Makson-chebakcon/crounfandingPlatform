package ru.cft.shift.crowdfundingplatformapi.service.auth;

import ru.cft.shift.crowdfundingplatformapi.dto.CreatePersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.TokensDto;

public interface AuthService {
    TokensDto register(CreatePersonDto createPersonDto);
}
