package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.RedactorProfileDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;

import java.util.UUID;

public interface RedactorProfileService {


    FullPersonDto redactor(UUID personId);
    Person newInfo(RedactorProfileDto redactorProfileDto);


}
