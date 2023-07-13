package ru.cft.shift.crowdfundingplatformapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.exception.NotFoundException;
import ru.cft.shift.crowdfundingplatformapi.mapper.PersonMapper;
import ru.cft.shift.crowdfundingplatformapi.repository.PersonRepository;
import ru.cft.shift.crowdfundingplatformapi.service.SearchPrifileService;

import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchProfileImpl implements SearchPrifileService{

    private final PersonMapper personMapper;
    private final PersonRepository personRepository;

    @Override
    public FullPersonDto getFullPersonDto(UUID personId) {
        Person person = getPersonById(personId);
        return personMapper.entityToFullDto(person);
    }
    @Override
    public Person getPersonById(UUID personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = '%s' не найден", personId)));
    }
}
