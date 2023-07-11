package ru.cft.shift.crowdfundingplatformapi.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.crowdfundingplatformapi.enumeration.Category;
import ru.cft.shift.crowdfundingplatformapi.enumeration.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFilteringParams {
    private String title;
    private Category category;
    private Status status;
}
