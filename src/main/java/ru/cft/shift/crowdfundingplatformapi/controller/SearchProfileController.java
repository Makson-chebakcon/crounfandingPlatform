package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.service.SearchPrifileService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
@Tag(name = "Поиск профиля")
public class SearchProfileController {

    private final SearchPrifileService searchPrifileService;
    @Operation(
            summary = "Получить информацию о пользователе",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/{profilleId}")
    public ResponseEntity<FullPersonDto> getOneProfile(@PathVariable UUID profileId){
        return  ResponseEntity.ok(searchPrifileService.getFullPersonDto(profileId));
    }
    @Operation(
            summary = "Получить информацию о всех пользователях",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("")
    public ResponseEntity<FullPersonDto> getAllProfile(@PathVariable UUID profileId){
        return  ResponseEntity.ok(searchPrifileService.getFullPersonDto(profileId));
    }
}
