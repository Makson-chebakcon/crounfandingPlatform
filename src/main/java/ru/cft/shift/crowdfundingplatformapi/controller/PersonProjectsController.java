package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shift.crowdfundingplatformapi.dto.project.FullProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectDto;
import ru.cft.shift.crowdfundingplatformapi.service.PersonProjectService;

import java.util.List;
import java.util.UUID;

import static ru.cft.shift.crowdfundingplatformapi.util.JwtExtractor.extractId;

@Slf4j
@RestController
@RequestMapping("/api/v1/persons/projects")
@RequiredArgsConstructor
@Tag(name = "Личные проекты")
public class PersonProjectsController {

    private final PersonProjectService personProjectService;

    @Operation(
            summary = "Получить свои проекты",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getMyProjects(Authentication authentication) {
        UUID personId = extractId(authentication);
        return ResponseEntity.ok(personProjectService.getMyProjects(personId));
    }

    @Operation(
            summary = "Получить свой проект",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/{projectId}")
    public ResponseEntity<FullProjectDto> getMyProject(Authentication authentication,
                                                       @PathVariable UUID projectId) {
        UUID personId = extractId(authentication);
        return ResponseEntity.ok(personProjectService.getMyProject(personId, projectId));
    }

}
