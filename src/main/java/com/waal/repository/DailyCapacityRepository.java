package com.waal.repository;

import com.waal.domain.DailyCapacity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyCapacityRepository extends JpaRepository<DailyCapacity, Long> {
    Optional<DailyCapacity> findByKindergartenIdAndDate(Long kindergartenId, LocalDate date);
}
