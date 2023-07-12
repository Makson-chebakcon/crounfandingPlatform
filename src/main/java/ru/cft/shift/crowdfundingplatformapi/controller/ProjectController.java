package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shift.crowdfundingplatformapi.dto.project.CreateProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.PagingResponse;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectPagingFilteringSortingRequest;
import ru.cft.shift.crowdfundingplatformapi.service.ProjectService;

import static ru.cft.shift.crowdfundingplatformapi.util.JwtExtractor.extractId;

@Slf4j
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Tag(name = "Настоящие")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(
            summary = "Создать проект.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public void createProject(@RequestBody @Valid CreateProjectDto dto,
                              Authentication auth) {
        projectService.createProject(extractId(auth), dto);
    }

    @Operation(summary = "Получить проекты.")
    @PostMapping("/search")
    public PagingResponse<ProjectDto> getProjects(@RequestBody @Valid ProjectPagingFilteringSortingRequest dto) {
        return projectService.getPublicProjects(dto);
    }

}
