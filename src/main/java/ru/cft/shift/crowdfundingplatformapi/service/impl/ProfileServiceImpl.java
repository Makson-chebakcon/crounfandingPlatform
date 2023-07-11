package ru.cft.shift.crowdfundingplatformapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.ResetPasswordDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.exception.BadRequestException;
import ru.cft.shift.crowdfundingplatformapi.exception.ConflictException;
import ru.cft.shift.crowdfundingplatformapi.exception.NotFoundException;
import ru.cft.shift.crowdfundingplatformapi.mapper.PersonMapper;
import ru.cft.shift.crowdfundingplatformapi.repository.PersonRepository;
import ru.cft.shift.crowdfundingplatformapi.service.MailService;
import ru.cft.shift.crowdfundingplatformapi.service.ProfileService;
import ru.cft.shift.crowdfundingplatformapi.util.PasswordGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

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

    @Override
    public void sendNewPassword(ResetPasswordDto dto) {
        Person person = personRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> {
                            log.error("Пользователь с почтой {} для сброса пароля не был найден", dto.getEmail());
                            return new BadRequestException("Не удалось сбросить пароль, так как данные неверны");
                        }
                );

        if (personFits(person, dto.getName(), dto.getSurname(), dto.getPatronymic())) {
            String newPassword = passwordGenerator.generatePassword();
            person.setPassword(passwordEncoder.encode(newPassword));
            personRepository.save(person);
            log.info("Новый пароль для пользователя с id {} успешно сгенерирован и сохранен", person.getId());
            mailService.sendNewPassword(person, newPassword);
        } else {
            log.error("ФИО для сброса пароля не совпало");
            throw new BadRequestException("Не удалось сбросить пароль, так как данные неверны");
        }

    }

    @Override
    public Person getPersonById(UUID personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = '%s' не найден", personId)));
    }

    private boolean personFits(Person person, String name, String surname, String patronymic) {
        return person.getName().equals(name)
                && person.getSurname().equals(surname)
                && person.getPatronymic().equals(patronymic);
    }
}

