package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.CreatePersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.CredentialsDto;
import ru.cft.shift.crowdfundingplatformapi.dto.TokensDto;

public interface AuthService {

    TokensDto register(CreatePersonDto createPersonDto);

    TokensDto login(CredentialsDto credentialsDto);

}
