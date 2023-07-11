package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.ResetPasswordDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;

import java.util.UUID;

public interface ProfileService {

    FullPersonDto getFullPersonDto(UUID personId);

    String confirmEmailAndGetFullName(UUID personId, UUID confirmCode);

    void sendNewPassword(ResetPasswordDto dto);

    Person getPersonById(UUID personId);

}
