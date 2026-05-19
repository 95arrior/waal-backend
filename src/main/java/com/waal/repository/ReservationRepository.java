package com.waal.repository;

import com.waal.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByKindergartenIdAndReservationDate(Long kindergartenId, LocalDate date);
    List<Reservation> findByDogIdOrderByReservationDateDesc(Long dogId);
    Optional<Reservation> findByDogIdAndKindergartenIdAndReservationDateAndStatusNot(
            Long dogId, Long kindergartenId, LocalDate date, Reservation.Status status);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.kindergarten.id = :kindergartenId " +
           "AND r.reservationDate = :date AND r.status IN ('PENDING', 'CONFIRMED')")
    int countActiveReservations(Long kindergartenId, LocalDate date);
}
