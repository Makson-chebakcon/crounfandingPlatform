package ru.cft.shift.crowdfundingplatformapi.service.auth;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.CreatePersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.TokensDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.entity.RefreshToken;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;
import ru.cft.shift.crowdfundingplatformapi.repository.PersonRepository;
import ru.cft.shift.crowdfundingplatformapi.repository.RefreshTokenRepository;
import ru.cft.shift.crowdfundingplatformapi.service.token.TokenService;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PersonRepository personRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenService tokenService;

    @Override
    public TokensDto register(CreatePersonDto createPersonDto) {
        Person person = Person.builder()
                .name(createPersonDto.getName())
                .surname(createPersonDto.getSurname())
                .patronymic(createPersonDto.getPatronymic())
                .email(createPersonDto.getEmail())
                .money(BigDecimal.ZERO)
                .role(PersonRole.ROLE_USER)
                .build();

        person = personRepository.save(person);
        String accessToken = tokenService.generateAccessToken(person);
        String refreshToken = getAndSaveRefreshToken(person);

        return new TokensDto(accessToken, refreshToken);
    }

    private String getAndSaveRefreshToken(Person person) {
        Pair<String, Date> refreshTokenAndExpiresDate = tokenService.generateRefreshTokenAndExpiresDate(person);

        RefreshToken refreshToken = RefreshToken.builder()
                .value(refreshTokenAndExpiresDate.getLeft())
                .createdAt(new Date())
                .expiredAt(refreshTokenAndExpiresDate.getRight())
                .owner(person)
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken.getValue();
    }

}
