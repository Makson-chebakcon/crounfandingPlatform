package ru.cft.shift.crowdfundingplatformapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.person.PersonDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;
import ru.cft.shift.crowdfundingplatformapi.mapper.PersonMapper;
import ru.cft.shift.crowdfundingplatformapi.repository.PersonRepository;
import ru.cft.shift.crowdfundingplatformapi.service.AdminService;
import ru.cft.shift.crowdfundingplatformapi.service.ProfileService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final PersonRepository personRepository;
    private final ProfileService profileService;
    private final PersonMapper personMapper;

    @Override
    public PersonDto changeRole(UUID personId, PersonRole role) {
        Person person = profileService.getPersonById(personId);
        person.setRole(role);
        person = personRepository.save(person);

        return personMapper.entityToDto(person);
    }

}
