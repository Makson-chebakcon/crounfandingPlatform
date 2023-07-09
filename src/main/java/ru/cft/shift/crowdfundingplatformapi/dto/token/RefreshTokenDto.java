package ru.cft.shift.crowdfundingplatformapi.dto.token;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {

    @NotBlank(message = "Токен не может быть пустым")
    private String refreshToken;

}
