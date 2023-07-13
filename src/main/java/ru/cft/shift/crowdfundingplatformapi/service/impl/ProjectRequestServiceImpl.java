package ru.cft.shift.crowdfundingplatformapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.PagingResponse;
import ru.cft.shift.crowdfundingplatformapi.dto.ProjectRequestDto;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsRequest;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsResponse;
import ru.cft.shift.crowdfundingplatformapi.entity.Project;
import ru.cft.shift.crowdfundingplatformapi.entity.ProjectRequest;
import ru.cft.shift.crowdfundingplatformapi.exception.NotFoundException;
import ru.cft.shift.crowdfundingplatformapi.mapper.ProjectRequestMapper;
import ru.cft.shift.crowdfundingplatformapi.repository.ProjectRepository;
import ru.cft.shift.crowdfundingplatformapi.repository.ProjectRequestRepository;
import ru.cft.shift.crowdfundingplatformapi.service.ProjectRequestService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectRequestServiceImpl implements ProjectRequestService {

    private final ProjectRequestRepository projectRequestRepository;
    private final ProjectRequestMapper projectRequestMapper;
    private final ProjectRepository projectRepository;

    @Override
    public PagingResponse<ProjectRequestDto> getProjectRequests(PagingParamsRequest dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("creationDate"));
        Page<ProjectRequest> projectRequestPage = projectRequestRepository.findAll(pageable);

        List<ProjectRequestDto> projectRequests = projectRequestPage
                .getContent()
                .stream()
                .map(projectRequestMapper::entityToDto)
                .toList();

        PagingParamsResponse pagingParamsResponse = new PagingParamsResponse(
                projectRequestPage.getTotalPages(),
                projectRequestPage.getTotalElements(),
                projectRequestPage.getNumber(),
                projectRequests.size()
        );

        return new PagingResponse<>(pagingParamsResponse, projectRequests);
    }

    @Override
    public ProjectRequestDto getProjectRequest(UUID projectRequestId) {
        ProjectRequest projectRequest = findProjectRequestById(projectRequestId);

        return projectRequestMapper.entityToDto(projectRequest);
    }

    @Transactional
    @Override
    public void updateProjectRequests(UUID projectRequestId, boolean isApproved) {
        ProjectRequest projectRequest = findProjectRequestById(projectRequestId);

        if (isApproved) {
            Project project = projectRequest.getProject();
            project.setIsApproved(true);
            projectRepository.save(project);
            projectRequestRepository.delete(projectRequest);
            log.info("Заявка {} для проекта {} одобрена", projectRequest.getId(), project.getId());
        } else {
            projectRequestRepository.delete(projectRequest);
            log.info("Заявка {} для проекта {} отклонена", projectRequest.getId(), projectRequest.getProject().getId());
        }
    }

    private ProjectRequest findProjectRequestById(UUID projectRequestId) {
        return projectRequestRepository
                .findById(projectRequestId)
                .orElseThrow(() -> new NotFoundException("Заявка с id " + projectRequestId + " не найден"));
    }
}
