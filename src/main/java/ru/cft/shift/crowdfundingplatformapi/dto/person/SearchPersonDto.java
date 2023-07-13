package ru.cft.shift.crowdfundingplatformapi.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPersonDto {
    private PagingParamsRequest pagingParams;
    private String name;
    private String surname;
    private String patronymic;
}
