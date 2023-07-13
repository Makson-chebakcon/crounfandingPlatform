package ru.cft.shift.crowdfundingplatformapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.crowdfundingplatformapi.dto.project.FullProjectDto;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDto {

    private UUID id;
    private Date creationDate;
    private FullProjectDto fullProjectDto;
}
