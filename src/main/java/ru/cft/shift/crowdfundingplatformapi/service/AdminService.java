package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.person.PersonDto;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;

import java.util.UUID;

public interface AdminService {
    PersonDto changeRole(UUID personId, PersonRole role);
}
