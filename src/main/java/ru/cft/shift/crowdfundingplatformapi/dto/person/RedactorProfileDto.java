package ru.cft.shift.crowdfundingplatformapi.dto.person;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import static ru.cft.shift.crowdfundingplatformapi.util.ValidationConstants.NAME_REGEX;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedactorProfileDto {

    @Pattern(regexp = NAME_REGEX, message = "Имя должно начинаться с заглавной буквы и не содержать пробелов")
    private String name;

    @Pattern(regexp = NAME_REGEX, message = "Фамилия должна начинаться с заглавной буквы и не содержать пробелов")
    private String surname;

    @Pattern(regexp = NAME_REGEX, message = "Отчество должно начинаться с заглавной буквы и не содержать пробелов")
    private String patronymic;

    private String bio;
}
