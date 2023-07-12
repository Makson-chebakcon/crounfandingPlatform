package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shift.crowdfundingplatformapi.dto.person.CreatePersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.CredentialsDto;
import ru.cft.shift.crowdfundingplatformapi.dto.token.RefreshTokenDto;
import ru.cft.shift.crowdfundingplatformapi.dto.token.TokensDto;
import ru.cft.shift.crowdfundingplatformapi.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Регистрация, логин")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Зарегистрироваться")
    @PostMapping("/register")
    ResponseEntity<TokensDto> register(@RequestBody @Valid CreatePersonDto createPersonDto) {
        return ResponseEntity.ok(authService.register(createPersonDto));
    }

    @Operation(summary = "Авторизоваться")
    @PostMapping("/login")
    ResponseEntity<TokensDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        return ResponseEntity.ok(authService.login(credentialsDto));
    }

    @Operation(summary = "Обновить пару токенов")
    @PostMapping("/tokens")
    ResponseEntity<TokensDto> refreshTokens(@RequestBody @Valid RefreshTokenDto refreshToken) {
        return ResponseEntity.ok(authService.refreshTokens(refreshToken));
    }

}
