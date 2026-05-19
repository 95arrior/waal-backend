package com.waal.repository;

import com.waal.domain.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    Optional<DailyReport> findByDogIdAndKindergartenIdAndReportDate(Long dogId, Long kindergartenId, LocalDate reportDate);
    List<DailyReport> findByDogIdOrderByReportDateDesc(Long dogId);
    List<DailyReport> findByKindergartenIdAndReportDate(Long kindergartenId, LocalDate reportDate);
    boolean existsByDogIdAndKindergartenIdAndReportDate(Long dogId, Long kindergartenId, LocalDate reportDate);
}
