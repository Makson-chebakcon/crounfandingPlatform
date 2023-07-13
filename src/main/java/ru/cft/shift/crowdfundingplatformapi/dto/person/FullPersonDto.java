package ru.cft.shift.crowdfundingplatformapi.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullPersonDto {
    private UUID id;
    private PersonRole personRole;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private BigDecimal money;
    private String bio;
    private Boolean emailIsConfirm;
    private UUID avatarId;
}
