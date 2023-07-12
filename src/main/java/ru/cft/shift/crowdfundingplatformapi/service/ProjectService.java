package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.project.CreateProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.PagingResponse;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectPagingFilteringSortingRequest;

import java.util.UUID;

public interface ProjectService {

    void createProject(UUID authorId, CreateProjectDto dto);

    PagingResponse<ProjectDto> getPublicProjects(ProjectPagingFilteringSortingRequest dto);

    ProjectDto getPublicProject(UUID projectId);

}
