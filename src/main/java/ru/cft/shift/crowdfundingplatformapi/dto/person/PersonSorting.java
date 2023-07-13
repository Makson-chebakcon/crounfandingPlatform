package ru.cft.shift.crowdfundingplatformapi.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonSorting {
    private Sort.Direction Surname;
    private Sort.Direction name;
    private Sort.Direction patronymic;
}
