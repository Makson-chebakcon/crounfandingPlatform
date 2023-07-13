package ru.cft.shift.crowdfundingplatformapi.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;
import ru.cft.shift.crowdfundingplatformapi.repository.PersonRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RootAdminCreator {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String email;

    @Value("${admin.password}")
    private String password;

    @EventListener(ApplicationReadyEvent.class)
    public void addRootAdmin() {
        Optional<Person> personOptional = personRepository.findByEmail(email);

        if (personOptional.isPresent()) {
            log.info("Корневой администратор уже существует");
            return;
        }

        Person person = new Person(
                UUID.randomUUID(),
                PersonRole.ROLE_ADMIN,
                passwordEncoder.encode(password),
                "Иван",
                "Иванов",
                "Иванович",
                null,
                email,
                true,
                null,
                null,
                BigDecimal.ZERO,
                Collections.emptyList(),
                Collections.emptyList(),
                null
        );

        personRepository.save(person);
        log.info("Добавлен корневой администратор");
    }

}
