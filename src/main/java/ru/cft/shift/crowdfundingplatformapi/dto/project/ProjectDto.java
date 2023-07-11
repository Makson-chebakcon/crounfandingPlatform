package ru.cft.shift.crowdfundingplatformapi.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.crowdfundingplatformapi.enumeration.Category;
import ru.cft.shift.crowdfundingplatformapi.enumeration.Status;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private UUID id;
    private String title;
    private String summary;
    private String targetAmount;
    private String collectedAmount;
    private UUID avatarId;
    private Date creationDate;
    private Date finishDate;
    private Category category;
    private Status status;
    private UUID authorId;
    private Boolean isApproved;
}
