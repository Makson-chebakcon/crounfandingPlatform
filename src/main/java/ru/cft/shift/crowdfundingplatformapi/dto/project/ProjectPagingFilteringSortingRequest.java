package ru.cft.shift.crowdfundingplatformapi.dto.project;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPagingFilteringSortingRequest {
    @NotNull(message = "Корневой объект пагинации не может быть равен null")
    private PagingParamsRequest pagingParams;

    @NotNull(message = "Корневой объект фильтрации не может быть равен null")
    private ProjectFilteringParams filteringParams;

    @NotNull(message = "Корневой объект с параметрами сортировок не может быть равен null")
    private ProjectSorting sorting;
}
