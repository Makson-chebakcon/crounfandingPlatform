package ru.cft.shift.crowdfundingplatformapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shift.crowdfundingplatformapi.dto.CreatePersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.TokensDto;
import ru.cft.shift.crowdfundingplatformapi.service.auth.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    ResponseEntity<TokensDto> register(@RequestBody CreatePersonDto createPersonDto) {
        return ResponseEntity.ok(authService.register(createPersonDto));
    }

}
