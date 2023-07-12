package ru.cft.shift.crowdfundingplatformapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.project.FullProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.entity.Project;
import ru.cft.shift.crowdfundingplatformapi.exception.NotFoundException;
import ru.cft.shift.crowdfundingplatformapi.mapper.ProjectMapper;
import ru.cft.shift.crowdfundingplatformapi.repository.ProjectRepository;
import ru.cft.shift.crowdfundingplatformapi.service.PersonProjectService;
import ru.cft.shift.crowdfundingplatformapi.service.ProfileService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonProjectServiceImpl implements PersonProjectService {

    private static final String PROJECT_NOT_FOUND = "Проект с id = %s не найден";
    private final ProfileService profileService;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectDto> getMyProjects(UUID personId) {
        Person person = profileService.getPersonById(personId);

        return person.getProjects()
                .stream()
                .map(projectMapper::entityToDto)
                .toList();
    }

    @Override
    public FullProjectDto getMyProject(UUID personId, UUID projectId) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format(PROJECT_NOT_FOUND, projectId)));

        if (!project.getAuthor().getId().equals(personId)) {
            throw new NotFoundException(String.format(PROJECT_NOT_FOUND, projectId));
        }

        return projectMapper.entityToFullDto(project);
    }

}
