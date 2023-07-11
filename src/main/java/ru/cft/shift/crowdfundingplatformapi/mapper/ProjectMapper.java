package ru.cft.shift.crowdfundingplatformapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.cft.shift.crowdfundingplatformapi.dto.project.CreateProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.FullProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectDto;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectFilteringParams;
import ru.cft.shift.crowdfundingplatformapi.entity.Project;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectMapper {

    @Mapping(target = "creationDate", expression = "java(new java.util.Date())")
    @Mapping(target = "collectedAmount", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "isApproved", expression = "java(false)")
    @Mapping(target = "status", expression = "java(ru.cft.shift.crowdfundingplatformapi.enumeration.Status.ACTIVE)")
    Project newDtoToEntity(CreateProjectDto dto);

    @Mapping(target = "authorId", source = "entity.author.id")
    FullProjectDto entityToFullDto(Project entity);

    @Mapping(target = "authorId", source = "entity.author.id")
    ProjectDto entityToDto(Project entity);

    Project filtersToEntity(ProjectFilteringParams filters);


}