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
import ru.cft.shift.crowdfundingplatformapi.dto.project.CreateProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.FullProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectPagingFilteringSortingRequest;
import ru.cft.shift.crowdfundingplatformapi.service.ProjectService;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.cft.shift.crowdfundingplatformapi.util.JwtExtractor.extractId;

@Slf4j
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Tag(name = "Проекты")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(
            summary = "Создать проект",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public void createProject(@RequestBody @Valid CreateProjectDto dto,
                              Authentication auth) {
        projectService.createProject(extractId(auth), dto);
    }

    @Operation(summary = "Получить аппрувнутые проекты")
    @PostMapping("/search")
    public PagingResponse<ProjectDto> getProjects(@RequestBody @Valid ProjectPagingFilteringSortingRequest dto) {
        return projectService.getPublicProjects(dto);
    }

    @Operation(summary = "Получить аппрувнутый проект")
    @GetMapping("/{projectId}")
    public ResponseEntity<FullProjectDto> getProject(@PathVariable UUID projectId) {
        return ResponseEntity.ok(projectService.getPublicProject(projectId));
    }

    @Operation(
            summary = "Спонсировать проект",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/{projectId}/sponsorship")
    public ResponseEntity<FullProjectDto> sponsorProject(@PathVariable UUID projectId,
                                                         @RequestParam BigDecimal money,
                                                         Authentication authentication
    ) {
        UUID personId = extractId(authentication);
        return ResponseEntity.ok(projectService.sponsorProject(projectId, money, personId));
    }
}
