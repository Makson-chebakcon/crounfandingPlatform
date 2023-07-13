package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.crowdfundingplatformapi.dto.GeneratePromoCodesDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.service.PromoCodesService;

import java.util.List;
import java.util.UUID;

import static ru.cft.shift.crowdfundingplatformapi.util.JwtExtractor.extractId;

@Tag(name = "Промокоды")
@RestController
@RequestMapping("/api/v1/promo-codes")
@RequiredArgsConstructor
public class PromoCodeController {

    private final PromoCodesService promoCodesService;

    @Operation(
            summary = "Сгенерировать промокоды",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public ResponseEntity<List<String>> generatePromoCodes(@RequestBody @Valid GeneratePromoCodesDto dto) {
        return ResponseEntity.ok(promoCodesService.generatePromoCodes(dto));
    }

    @Operation(
            summary = "Активировать промокод",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/{promoCode}")
    public ResponseEntity<FullPersonDto> activatePromoCode(@PathVariable String promoCode,
                                                           Authentication authentication) {
        UUID id = extractId(authentication);
        return ResponseEntity.ok(promoCodesService.activate(id, promoCode));
    }

}
