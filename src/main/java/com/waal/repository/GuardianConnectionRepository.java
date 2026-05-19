package com.waal.repository;

import com.waal.domain.GuardianConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuardianConnectionRepository extends JpaRepository<GuardianConnection, Long> {
    List<GuardianConnection> findByGuardianIdAndStatus(Long guardianId, GuardianConnection.Status status);
    List<GuardianConnection> findByKindergartenIdAndStatus(Long kindergartenId, GuardianConnection.Status status);
    Optional<GuardianConnection> findByGuardianIdAndKindergartenIdAndDogId(Long guardianId, Long kindergartenId, Long dogId);
    boolean existsByGuardianIdAndKindergartenIdAndDogId(Long guardianId, Long kindergartenId, Long dogId);
}
