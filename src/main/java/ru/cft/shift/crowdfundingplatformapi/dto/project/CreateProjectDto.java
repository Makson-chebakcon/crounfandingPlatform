package ru.cft.shift.crowdfundingplatformapi.dto.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.cft.shift.crowdfundingplatformapi.enumeration.Category;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectDto {

    @Schema(example = "Название")
    @NotNull(message = "Название проекта не может быть равно null")
    @Length(max = 64, min = 1, message = "Название проекта не может превышать 64 символа или быть меньше 1 символа")
    private String title;

    @Schema(example = "Короткое описание для превью")
    @NotNull(message = "Краткое описание не может быть равно null")
    @Length(max = 256, min = 1, message = "Длина не может быть больше 256 символов или меньше 1 символа")
    private String summary;

    @Schema(example = "Ну тут должно быть очент длинное описание")
    @Length(max = 32768, min = 1, message = "Описание проекта должно быть не меньше 1 символа и не больше 32768")
    @NotNull(message = "Описание не может быть равно null")
    private String description;

    @Schema(example = "100000")
    @Min(value = 1000, message = "Цель не может быть меньше 1000")
    @NotNull(message = "Цель не может быть равна null")
    private BigDecimal targetAmount;

    @NotNull(message = "Категория не может быть равна null")
    private Category category;

    @NotNull(message = "Должна быть дата окончания проекта")
    private Date finishDate;

    @NotNull(message = "У проекта должна быть картинка")
    private UUID avatarId;

    @NotNull(message = "Список вложений не может быть равен null, но может быть пустым.")
    private List<UUID> attachmentIds;
}
