package ru.cft.shift.crowdfundingplatformapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.shift.crowdfundingplatformapi.entity.Project;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
