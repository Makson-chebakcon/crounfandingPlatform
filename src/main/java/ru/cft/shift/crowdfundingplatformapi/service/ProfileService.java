package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;

import java.util.UUID;

public interface ProfileService {

    FullPersonDto getFullPersonDto(UUID personId);

}
