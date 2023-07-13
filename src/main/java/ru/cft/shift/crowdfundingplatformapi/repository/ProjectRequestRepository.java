package ru.cft.shift.crowdfundingplatformapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cft.shift.crowdfundingplatformapi.entity.ProjectRequest;

import java.util.UUID;

@Repository
public interface ProjectRequestRepository extends JpaRepository<ProjectRequest, UUID> {
}
