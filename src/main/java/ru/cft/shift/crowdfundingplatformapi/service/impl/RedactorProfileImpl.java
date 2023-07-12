package ru.cft.shift.crowdfundingplatformapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.RedactorProfileDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.exception.NotFoundException;
import ru.cft.shift.crowdfundingplatformapi.repository.PersonRepository;
import ru.cft.shift.crowdfundingplatformapi.service.RedactorProfileService;

import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class RedactorProfileImpl implements RedactorProfileService {

    private final PersonRepository personRepository;


    @Override
    public FullPersonDto redactor(UUID personId) {
        RedactorProfileDto redactorProfileDto = new RedactorProfileDto();
        Person person = personRepository
                .findById(personId)
                .orElseThrow(() -> {
                            log.error("Пользователь с ID {} для изменения данных профиля не найден", personId);
                            return new NotFoundException("Не удалось внести изменения");
                        }
                );
        Person personUpdate = newInfo(redactorProfileDto);
        person.setName(personUpdate.getName());
        person.setSurname(personUpdate.getSurname());
        person.setPatronymic(personUpdate.getPatronymic());
        person.setBio(personUpdate.getBio());
        personRepository.save(person);
        return null;


    }

    @Override
    public Person newInfo(RedactorProfileDto redactorProfileDto) {

        return Person.builder().name(redactorProfileDto.getName())
                .surname(redactorProfileDto.getSurname()).patronymic(redactorProfileDto.getPatronymic())
                .bio(redactorProfileDto.getBio()).build();

    }
}
