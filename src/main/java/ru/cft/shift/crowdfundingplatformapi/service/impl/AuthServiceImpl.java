package ru.cft.shift.crowdfundingplatformapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.person.CreatePersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.CredentialsDto;
import ru.cft.shift.crowdfundingplatformapi.dto.token.RefreshTokenDto;
import ru.cft.shift.crowdfundingplatformapi.dto.token.TokensDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.entity.RefreshToken;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;
import ru.cft.shift.crowdfundingplatformapi.exception.ConflictException;
import ru.cft.shift.crowdfundingplatformapi.exception.UnauthorizedException;
import ru.cft.shift.crowdfundingplatformapi.repository.PersonRepository;
import ru.cft.shift.crowdfundingplatformapi.repository.RefreshTokenRepository;
import ru.cft.shift.crowdfundingplatformapi.service.AuthService;
import ru.cft.shift.crowdfundingplatformapi.service.MailService;
import ru.cft.shift.crowdfundingplatformapi.service.TokenService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PersonRepository personRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public TokensDto register(CreatePersonDto createPersonDto) {
        if (personRepository.existsByEmail(createPersonDto.getEmail())) {
            throw new ConflictException(String.format("Пользователь с email = '%s' уже существует", createPersonDto.getEmail()));
        }

        Person person = Person.builder()
                .name(createPersonDto.getName())
                .surname(createPersonDto.getSurname())
                .patronymic(createPersonDto.getPatronymic())
                .email(createPersonDto.getEmail())
                .money(BigDecimal.ZERO)
                .role(PersonRole.ROLE_USER)
                .password(passwordEncoder.encode(createPersonDto.getPassword()))
                .emailConfirmCode(UUID.randomUUID())
                .emailIsConfirm(false)
                .emailConfirmCodeExpiresAt(LocalDateTime.now().plusMinutes(60))
                .build();

        person = personRepository.save(person);
        String accessToken = tokenService.generateAccessToken(person);
        String refreshToken = getAndSaveRefreshToken(person);

        mailService.newUserMessage(person);

        return new TokensDto(accessToken, refreshToken);
    }

    @Override
    public TokensDto login(CredentialsDto credentialsDto) {
        Person person = personRepository
                .findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Некорректный email и/или пароль"));

        if (!passwordEncoder.matches(credentialsDto.getPassword(), person.getPassword())) {
            throw new UnauthorizedException("Некоррктный email и/или пароль");
        }

        String accessToken = tokenService.generateAccessToken(person);
        String refreshToken = getAndSaveRefreshToken(person);


        return new TokensDto(accessToken, refreshToken);
    }

    @Transactional
    @Override
    public TokensDto refreshTokens(RefreshTokenDto refreshTokenDto) {
        RefreshToken token = refreshTokenRepository
                .findByValue(refreshTokenDto.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedException("Некорректный токен"));

        if (!token.getExpiredAt().after(new Date())) {
            throw new UnauthorizedException("Срок действия токена истёк");
        }

        refreshTokenRepository.delete(token);

        Person person = token.getOwner();

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
