package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.crowdfundingplatformapi.dto.PagingResponse;
import ru.cft.shift.crowdfundingplatformapi.dto.ProjectRequestDto;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsRequest;
import ru.cft.shift.crowdfundingplatformapi.service.ProjectRequestService;

import java.util.UUID;

@Tag(name = "Заявки проектов")
@RestController
@RequestMapping("/api/v1/project-requests")
@RequiredArgsConstructor
public class ProjectRequestController {

    private final ProjectRequestService projectRequestService;

    @Operation(
            summary = "Получить заявки на одобрение проектов",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public ResponseEntity<PagingResponse<ProjectRequestDto>> getProjectRequests(@RequestBody @Valid PagingParamsRequest dto) {
        return ResponseEntity.ok(projectRequestService.getProjectRequests(dto));
    }

    @Operation(
            summary = "Получить заявку на одобрение проектов",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/{projectRequestId}")
    public ResponseEntity<ProjectRequestDto> getProjectRequest(@PathVariable UUID projectRequestId) {
        return ResponseEntity.ok(projectRequestService.getProjectRequest(projectRequestId));
    }

    @Operation(
            summary = "Одобрить или отклонить заявку",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/{projectRequestId}")
    public void updateProjectRequests(@PathVariable UUID projectRequestId,
                                      @RequestParam boolean isApproved) {
        projectRequestService.updateProjectRequests(projectRequestId, isApproved);
    }


}
