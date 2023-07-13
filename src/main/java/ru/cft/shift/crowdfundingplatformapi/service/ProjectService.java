package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.PagingResponse;
import ru.cft.shift.crowdfundingplatformapi.dto.project.CreateProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.FullProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectPagingFilteringSortingRequest;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProjectService {

    void createProject(UUID authorId, CreateProjectDto dto);

    PagingResponse<ProjectDto> getPublicProjects(ProjectPagingFilteringSortingRequest dto);

    FullProjectDto getPublicProject(UUID projectId);

    FullProjectDto sponsorProject(UUID projectId, BigDecimal money, UUID personId);
}
