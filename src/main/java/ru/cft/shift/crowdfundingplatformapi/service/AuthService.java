package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.person.CreatePersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.CredentialsDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.TokensDto;

public interface AuthService {

    TokensDto register(CreatePersonDto createPersonDto);

    TokensDto login(CredentialsDto credentialsDto);

}
