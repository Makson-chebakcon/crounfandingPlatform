package ru.cft.shift.crowdfundingplatformapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.crowdfundingplatformapi.dto.api.PagingParamsResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponse<T> {
    private PagingParamsResponse pagingParams;
    private List<T> content;
}
