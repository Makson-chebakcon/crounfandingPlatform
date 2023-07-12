package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.service.RedactorProfileService;

import java.util.UUID;

import static ru.cft.shift.crowdfundingplatformapi.util.JwtExtractor.extractId;
@Slf4j
@RestController
@RequestMapping("/api/v1/profiles/redactor")
@RequiredArgsConstructor
@Tag(name = "Настоящие")
public class RedactorProfileController {

    private final RedactorProfileService redactorProfileService;

    @Operation(
            summary = "Редактировать профиль.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
     @GetMapping
    public ResponseEntity<FullPersonDto> getProfileId(Authentication authentication) {
        UUID id = extractId(authentication);
        return ResponseEntity.ok(redactorProfileService.redactor(id));
    }



}
