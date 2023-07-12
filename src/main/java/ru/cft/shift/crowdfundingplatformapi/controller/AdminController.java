package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.crowdfundingplatformapi.dto.person.PersonDto;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;
import ru.cft.shift.crowdfundingplatformapi.service.AdminService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(
            summary = "Поменять пользователю роль.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/persons/{personId}/role")
    public PersonDto changeRole(@PathVariable UUID personId,
                                @RequestParam PersonRole role) {
        return adminService.changeRole(personId, role);
    }

}
