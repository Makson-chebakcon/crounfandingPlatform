package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.ProjectRequestDto;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsRequest;
import ru.cft.shift.crowdfundingplatformapi.dto.project.PagingResponse;

import java.util.UUID;

public interface ProjectRequestService {
    PagingResponse<ProjectRequestDto> getProjectRequests(PagingParamsRequest dto);

    ProjectRequestDto getProjectRequest(UUID projectRequestId);

    void updateProjectRequests(UUID projectRequestId, boolean isApproved);
}
