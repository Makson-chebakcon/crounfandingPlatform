package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.PagingResponse;
import ru.cft.shift.crowdfundingplatformapi.dto.ProjectRequestDto;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsRequest;

import java.util.UUID;

public interface ProjectRequestService {
    PagingResponse<ProjectRequestDto> getProjectRequests(PagingParamsRequest dto);

    ProjectRequestDto getProjectRequest(UUID projectRequestId);

    void updateProjectRequests(UUID projectRequestId, boolean isApproved);
}
