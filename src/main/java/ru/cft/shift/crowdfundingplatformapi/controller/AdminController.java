package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.crowdfundingplatformapi.dto.GeneratePromoCodesDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.PersonDto;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;
import ru.cft.shift.crowdfundingplatformapi.service.AdminService;
import ru.cft.shift.crowdfundingplatformapi.service.PromoCodesService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Админка")
public class AdminController {

    private final AdminService adminService;
    private final PromoCodesService promoCodesService;

    @Operation(
            summary = "Поменять пользователю роль.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/persons/{personId}/role")
    public ResponseEntity<PersonDto> changeRole(@PathVariable UUID personId,
                                                @RequestParam PersonRole role) {
        return ResponseEntity.ok(adminService.changeRole(personId, role));
    }

    @Operation(
            summary = "Сгенерировать промокоды",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/promo-codes")
    public ResponseEntity<List<String>> generatePromoCodes(@RequestBody @Valid GeneratePromoCodesDto dto) {
        return ResponseEntity.ok(promoCodesService.generatePromoCodes(dto));
    }
}
