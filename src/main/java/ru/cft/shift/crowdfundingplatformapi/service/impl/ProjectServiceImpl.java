package ru.cft.shift.crowdfundingplatformapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.PagingResponse;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsResponse;
import ru.cft.shift.crowdfundingplatformapi.dto.project.*;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.entity.Project;
import ru.cft.shift.crowdfundingplatformapi.entity.ProjectRequest;
import ru.cft.shift.crowdfundingplatformapi.enumeration.Category;
import ru.cft.shift.crowdfundingplatformapi.enumeration.Status;
import ru.cft.shift.crowdfundingplatformapi.exception.BadRequestException;
import ru.cft.shift.crowdfundingplatformapi.exception.NotFoundException;
import ru.cft.shift.crowdfundingplatformapi.mapper.ProjectMapper;
import ru.cft.shift.crowdfundingplatformapi.repository.PersonRepository;
import ru.cft.shift.crowdfundingplatformapi.repository.ProjectRepository;
import ru.cft.shift.crowdfundingplatformapi.repository.ProjectRequestRepository;
import ru.cft.shift.crowdfundingplatformapi.service.FileStorageService;
import ru.cft.shift.crowdfundingplatformapi.service.ProfileService;
import ru.cft.shift.crowdfundingplatformapi.service.ProjectService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    public static final String PROJECT_NOT_FOUND = "Проект с id %s не найден";
    private static final String TITLE_PROPERTY = "title";
    private static final String CATEGORY_PROPERTY = "category";
    private static final String STATUS_PROPERTY = "status";
    private static final String CREATION_DATE_PROPERTY = "creationDate";
    private static final String TARGET_AMOUNT_PROPERTY = "targetAmount";

    private final ProfileService profileService;
    private final ProjectMapper projectMapper;
    private final FileStorageService fileStorageService;
    private final ProjectRepository projectRepository;
    private final ProjectRequestRepository projectRequestRepository;
    private final PersonRepository personRepository;

    @Transactional
    @Override
    public void createProject(UUID authorId, CreateProjectDto dto) {
        if (dto.getCategory() == Category.ALL) {
            throw new BadRequestException("Категория ALL недоступна для создания проекта");
        }

        fileStorageService.existFile(dto.getAvatarId());
        fileStorageService.existFiles(dto.getAttachmentIds());

        ProjectRequest projectRequest = ProjectRequest.builder()
                .creationDate(new Date())
                .build();
        projectRequest = projectRequestRepository.save(projectRequest);

        Person author = profileService.getPersonById(authorId);
        Project project = projectMapper.newDtoToEntity(dto);
        project.setAuthor(author);
        project.setRequest(projectRequest);

        projectRepository.save(project);
    }

    @Override
    public PagingResponse<ProjectDto> getPublicProjects(ProjectPagingFilteringSortingRequest dto) {
        Project exampleEntity = projectMapper.filtersToEntity(dto.getFilteringParams());

        if (exampleEntity.getCategory() == Category.ALL) {
            exampleEntity.setCategory(null);
        }

        if (exampleEntity.getStatus() == Status.ALL) {
            exampleEntity.setStatus(null);
        }

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

    @Override
    public FullProjectDto getPublicProject(UUID projectId) {
        Project project = getProjectById(projectId);

        if (Boolean.FALSE.equals(project.getIsApproved())) {
            throw new NotFoundException(String.format(PROJECT_NOT_FOUND, projectId));
        }

        return projectMapper.entityToFullDto(project);
    }

    @Transactional
    @Override
    public FullProjectDto sponsorProject(UUID projectId, BigDecimal money, UUID personId) {
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Некорректная сумма для пополнения");
        }

        Project project = getProjectById(projectId);
        Person person = profileService.getPersonById(personId);

        if (person.getMoney().compareTo(money) < 0) {
            throw new BadRequestException("Недостаточно средств");
        }

        person.setMoney(person.getMoney().subtract(money));
        project.setCollectedAmount(project.getCollectedAmount().add(money));

        if (project.getCollectedAmount().compareTo(project.getTargetAmount()) >= 0) {
            project.setFinishDate(new Date());
            project.setStatus(Status.FINISHED);
        }

        project = projectRepository.save(project);
        personRepository.save(person);

        return projectMapper.entityToFullDto(project);
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

    private Project getProjectById(UUID projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format(PROJECT_NOT_FOUND, projectId)));
    }

}
