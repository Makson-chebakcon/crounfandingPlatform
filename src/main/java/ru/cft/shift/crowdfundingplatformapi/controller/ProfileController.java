package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.crowdfundingplatformapi.dto.PagingResponse;
import ru.cft.shift.crowdfundingplatformapi.dto.person.*;
import ru.cft.shift.crowdfundingplatformapi.service.ProfileService;

import java.util.UUID;

import static ru.cft.shift.crowdfundingplatformapi.util.JwtExtractor.extractId;

@Slf4j
@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@Tag(name = "Профиль пользователя")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(
            summary = "Просмотр информации о себе",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    public ResponseEntity<FullPersonDto> getProfile(Authentication authentication) {
        UUID id = extractId(authentication);
        return ResponseEntity.ok(profileService.getFullPersonDto(id));
    }

    @Operation(summary = "Выслать на почту новый пароль")
    @PostMapping("/reset-password")
    public void sendNewPassword(@RequestBody @Valid ResetPasswordDto resetPasswordDto) {
        profileService.sendNewPassword(resetPasswordDto);
    }

    @Operation(
            summary = "Изменить профиль",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping
    public ResponseEntity<FullPersonDto> updateProfile(Authentication authentication,
                                                       @RequestBody @Valid UpdatePersonDto dto) {
        UUID id = extractId(authentication);
        return ResponseEntity.ok(profileService.updateProfile(id, dto));
    }

    @Operation(summary = "Получить профиль пользователя")
    @GetMapping("/{personId}")
    public ResponseEntity<PersonDto> getProfileById(@PathVariable UUID personId) {
        return ResponseEntity.ok(profileService.getPersonDto(personId));
    }

    @Operation(
            summary = "Поиск пользователей",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/search")
    public PagingResponse<PersonDto> getProfiles(@RequestBody SearchPersonDto dto) {
        return profileService.getProfiles(dto);
    }

}
