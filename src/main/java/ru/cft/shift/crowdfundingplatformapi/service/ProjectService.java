package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.project.*;

import java.util.UUID;

public interface ProjectService {

    void createProject(UUID authorId, CreateProjectDto dto);

    PagingResponse<ProjectDto> getPublicProjects(ProjectPagingFilteringSortingRequest dto);

    FullProjectDto getPublicProject(UUID projectId);
}
