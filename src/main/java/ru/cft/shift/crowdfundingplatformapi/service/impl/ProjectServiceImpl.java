package ru.cft.shift.crowdfundingplatformapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsResponse;
import ru.cft.shift.crowdfundingplatformapi.dto.project.*;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.entity.Project;
import ru.cft.shift.crowdfundingplatformapi.mapper.ProjectMapper;
import ru.cft.shift.crowdfundingplatformapi.repository.ProjectRepository;
import ru.cft.shift.crowdfundingplatformapi.service.FileStorageService;
import ru.cft.shift.crowdfundingplatformapi.service.ProfileService;
import ru.cft.shift.crowdfundingplatformapi.service.ProjectService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private static final String TITLE_PROPERTY = "title";
    private static final String CATEGORY_PROPERTY = "category";
    private static final String STATUS_PROPERTY = "status";
    private static final String CREATION_DATE_PROPERTY = "creationDate";
    private static final String TARGET_AMOUNT_PROPERTY = "targetAmount";

    private final ProfileService profileService;
    private final ProjectMapper projectMapper;
    private final FileStorageService fileStorageService;
    private final ProjectRepository projectRepository;

    @Override
    public void createProject(UUID authorId, CreateProjectDto dto) {
        fileStorageService.existFile(dto.getAvatarId());
        fileStorageService.existFiles(dto.getAttachmentIds());

        Person author = profileService.getPersonById(authorId);

        Project project = projectMapper.newDtoToEntity(dto);
        project.setAuthor(author);

        projectRepository.save(project);
    }

    @Override
    public PagingResponse<ProjectDto> getPublicProjects(ProjectPagingFilteringSortingRequest dto) {
        Project exampleEntity = projectMapper.filtersToEntity(dto.getFilteringParams());
        exampleEntity.setIsApproved(true);

        Example<Project> projectExample = Example.of(exampleEntity, buildExampleMatcher());
        Pageable pageable = buildPageable(dto);
        Page<Project> projectPage = projectRepository.findAll(projectExample, pageable);

        List<ProjectDto> projects = projectPage.getContent()
                .stream()
                .map(projectMapper::entityToDto)
                .toList();

        PagingParamsResponse pagingParamsResponse = new PagingParamsResponse(
                projectPage.getTotalPages(),
                projectPage.getTotalElements(),
                projectPage.getNumber(),
                projects.size()
        );

        return new PagingResponse<>(pagingParamsResponse, projects);

    }

    private Pageable buildPageable(ProjectPagingFilteringSortingRequest dto) {
        int page = dto.getPagingParams().getPage();
        int size = dto.getPagingParams().getSize();
        return PageRequest.of(page, size, buildOrders(dto.getSorting()));
    }

    private Sort buildOrders(ProjectSorting sorting) {
        return Sort.by(
                List.of(
                        new Sort.Order(sorting.getTitle(), TITLE_PROPERTY),
                        new Sort.Order(sorting.getCategory(), CATEGORY_PROPERTY),
                        new Sort.Order(sorting.getStatus(), STATUS_PROPERTY),
                        new Sort.Order(sorting.getCreationDate(), CREATION_DATE_PROPERTY),
                        new Sort.Order(sorting.getTargetAmount(), TARGET_AMOUNT_PROPERTY)
                )
        );
    }

    private ExampleMatcher buildExampleMatcher() {
        return ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    }

}
