package ru.cft.shift.crowdfundingplatformapi.dto.person;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsRequest;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectFilteringParams;
import ru.cft.shift.crowdfundingplatformapi.dto.project.ProjectSorting;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonPagingfilteringSortingRequest {
    @NotNull(message = "Корневой объект пагинации не может быть равен null")
    private PagingParamsRequest pagingParams;

    @NotNull(message = "Корневой объект с параметрами сортировок не может быть равен null")
    private PersonSorting  sorting;

}
