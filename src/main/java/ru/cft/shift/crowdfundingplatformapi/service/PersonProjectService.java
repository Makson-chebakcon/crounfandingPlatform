package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.project.FullProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectDto;

import java.util.List;
import java.util.UUID;

public interface PersonProjectService {

    List<ProjectDto> getMyProjects(UUID personId);

    FullProjectDto getMyProject(UUID personId, UUID projectId);

}
