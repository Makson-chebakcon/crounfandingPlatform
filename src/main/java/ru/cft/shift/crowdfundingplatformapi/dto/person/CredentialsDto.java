package ru.cft.shift.crowdfundingplatformapi.dto.person;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDto {
    @Schema(example = "example@domain.com")
    @NotBlank(message = "Почта не может быть пустой")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
