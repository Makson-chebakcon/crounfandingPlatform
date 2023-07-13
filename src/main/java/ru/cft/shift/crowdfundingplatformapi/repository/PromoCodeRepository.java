package ru.cft.shift.crowdfundingplatformapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cft.shift.crowdfundingplatformapi.entity.PromoCode;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, UUID> {

    Optional<PromoCode> findByValue(String value);

    boolean existsByValue(String value);

}
