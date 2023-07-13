package ru.cft.shift.crowdfundingplatformapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.cft.shift.crowdfundingplatformapi.dto.ProjectRequestDto;
import ru.cft.shift.crowdfundingplatformapi.entity.ProjectRequest;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectRequestMapper {

    @Mapping(source = "project", target = "fullProjectDto")
    @Mapping(source = "project.author.id", target = "fullProjectDto.authorId")
    ProjectRequestDto entityToDto(ProjectRequest projectRequest);

}
