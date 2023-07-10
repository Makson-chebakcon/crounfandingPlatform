package ru.cft.shift.crowdfundingplatformapi.dto.person;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @NotBlank(message = "Фамилия не может быть пустым")
    private String surname;

    @NotBlank(message = "Отчество не может быть пустым")
    private String patronymic;

    @NotBlank(message = "Почта не может быть пустой")
    private String email;
}
