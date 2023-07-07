package ru.cft.shift.crowdfundingplatformapi.mapper;

import org.springframework.stereotype.Component;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;

@Component
public class PersonMapper {

    public FullPersonDto entityToFullDto(Person entity) {
        return new FullPersonDto(
                entity.getId(),
                entity.getRole(),
                entity.getName(),
                entity.getSurname(),
                entity.getPatronymic(),
                entity.getEmail(),
                entity.getMoney(),
                entity.getBio()
        );
    }

}
