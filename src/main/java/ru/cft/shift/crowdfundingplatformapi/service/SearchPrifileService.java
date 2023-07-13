package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;

import java.util.UUID;

public interface SearchPrifileService {
    FullPersonDto getFullPersonDto(UUID personId);

    Person getPersonById(UUID personId);
}
