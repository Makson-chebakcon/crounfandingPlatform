package ru.cft.shift.crowdfundingplatformapi.dto.person;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.cft.shift.crowdfundingplatformapi.util.ValidationConstants.EMAIL_REGEX;
import static ru.cft.shift.crowdfundingplatformapi.util.ValidationConstants.NAME_REGEX;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonDto {

    @Pattern(regexp = NAME_REGEX, message = "Имя должно начинаться с заглавной буквы и не содержать пробелов")
    private String name;

    @Pattern(regexp = NAME_REGEX, message = "Фамилия должна начинаться с заглавной буквы и не содержать пробелов")
    private String surname;

    @Pattern(regexp = NAME_REGEX, message = "Отчество должно начинаться с заглавной буквы и не содержать пробелов")
    private String patronymic;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Schema(example = "email@domain.org")
    @Pattern(regexp = EMAIL_REGEX, message = "Почта не соответствует формату")
    private String email;
}
