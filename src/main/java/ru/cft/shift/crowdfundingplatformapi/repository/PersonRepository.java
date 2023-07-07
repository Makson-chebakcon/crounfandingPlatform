package ru.cft.shift.crowdfundingplatformapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
