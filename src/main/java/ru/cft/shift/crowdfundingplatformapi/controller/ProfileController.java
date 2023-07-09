package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.service.ProfileService;

import java.util.UUID;

import static ru.cft.shift.crowdfundingplatformapi.util.JwtExtractor.extractId;

@Slf4j
@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@Tag(name = "Настоящие")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(
            summary = "Просмотр информации о себе.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    public ResponseEntity<FullPersonDto> getProfile(Authentication authentication) {
        UUID id = extractId(authentication);
        return ResponseEntity.ok(profileService.getFullPersonDto(id));
    }

}
