package ru.cft.shift.crowdfundingplatformapi.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSorting {
    private Sort.Direction title;
    private Sort.Direction creationDate;
    private Sort.Direction targetAmount;
    private Sort.Direction category;
    private Sort.Direction status;
}
