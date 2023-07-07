package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.FullPersonDto;

import java.util.UUID;

public interface ProfileService {

    FullPersonDto getFullPersonDto(UUID personId);

}
