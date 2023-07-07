package ru.cft.shift.crowdfundingplatformapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cft.shift.crowdfundingplatformapi.entity.FileMetaInformation;

import java.util.UUID;

@Repository
public interface MetaInformationRepository extends JpaRepository<FileMetaInformation, UUID> {
}
