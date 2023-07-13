package ru.cft.shift.crowdfundingplatformapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeneratePromoCodesDto {

    @Min(value = 1, message = "Количество промокодов не может быть меньше 1")
    @Max(value = 50, message = "Количество промокодов не может быть больше 50")
    private int count;

    @Min(value = 100, message = "Значение промокода не может быть меньше 100")
    private int amount;
}
