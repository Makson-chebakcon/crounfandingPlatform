package ru.cft.shift.crowdfundingplatformapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.exception.BadRequestException;
import ru.cft.shift.crowdfundingplatformapi.exception.ConflictException;
import ru.cft.shift.crowdfundingplatformapi.exception.NotFoundException;
import ru.cft.shift.crowdfundingplatformapi.mapper.PersonMapper;
import ru.cft.shift.crowdfundingplatformapi.repository.PersonRepository;
import ru.cft.shift.crowdfundingplatformapi.service.ProfileService;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public FullPersonDto getFullPersonDto(UUID personId) {
        Person person = getPersonById(personId);
        return personMapper.entityToFullDto(person);
    }

    @Override
    public String confirmEmailAndGetFullName(UUID personId, UUID confirmCode) {
        Person person = getPersonById(personId);

        if (Boolean.TRUE.equals(person.getEmailIsConfirm())) {
            throw new ConflictException("Почта у пользователя с id " + person.getId() + " уже подтверждена");
        }

        if (confirmCode.equals(person.getEmailConfirmCode())
                && LocalDateTime.now().isBefore(person.getEmailConfirmCodeExpiresAt())
        ) {
            person.setEmailIsConfirm(true);
            person.setEmailConfirmCode(null);
            person.setEmailConfirmCodeExpiresAt(null);

            personRepository.save(person);

            return person.getName() + " " + person.getSurname() + " " + person.getPatronymic();
        }

        throw new BadRequestException("Не удалось подтвердить почту пользователю, так как" +
                "не подошел код подтверждения или срок действия ссылки истёк");
    }

    private Person getPersonById(UUID personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = '%s' не найден", personId)));
    }
}

