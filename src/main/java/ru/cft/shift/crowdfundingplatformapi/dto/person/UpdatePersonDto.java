package ru.cft.shift.crowdfundingplatformapi.dto.person;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

import static ru.cft.shift.crowdfundingplatformapi.util.ValidationConstants.NAME_REGEX;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePersonDto {
    @Pattern(regexp = NAME_REGEX, message = "Имя должно начинаться с заглавной буквы и не содержать пробелов")
    private String name;

    @Pattern(regexp = NAME_REGEX, message = "Фамилия должна начинаться с заглавной буквы и не содержать пробелов")
    private String surname;

    @Pattern(regexp = NAME_REGEX, message = "Отчество должно начинаться с заглавной буквы и не содержать пробелов")
    private String patronymic;

    @Length(max = 32000, message = "Раздел о себе не может превышать 32000 символов")
    private String bio;

    private UUID avatarId;

}
