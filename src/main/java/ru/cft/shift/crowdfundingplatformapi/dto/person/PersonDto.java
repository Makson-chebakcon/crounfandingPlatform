package ru.cft.shift.crowdfundingplatformapi.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private UUID id;
    private PersonRole role;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private UUID avatarId;
}
