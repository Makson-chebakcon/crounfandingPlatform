package ru.cft.shift.crowdfundingplatformapi.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingParamsResponse {
    private long totalPages;
    private long totalElements;
    private long page;
    private long size;
}
