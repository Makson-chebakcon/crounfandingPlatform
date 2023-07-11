package ru.cft.shift.crowdfundingplatformapi.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingParamsRequest {

    @Min(0)
    @Schema(description = "Номер страницы", minimum = "0")
    private int page;

    @Min(1)
    @Schema(description = "Размер страницы", minimum = "1")
    private int size;

}
