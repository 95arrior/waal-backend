package com.waal.service;

import com.waal.common.exception.ErrorCode;
import com.waal.common.exception.WaalException;
import com.waal.domain.*;
import com.waal.dto.reservation.DailyCapacityResponse;
import com.waal.dto.reservation.ReservationCancelRequest;
import com.waal.dto.reservation.ReservationCreateRequest;
import com.waal.dto.reservation.ReservationResponse;
import com.waal.repository.DailyCapacityRepository;
import com.waal.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final DailyCapacityRepository dailyCapacityRepository;
    private final DogService dogService;
    private final KindergartenService kindergartenService;

    @Transactional
    public ReservationResponse create(Long userId, ReservationCreateRequest request) {
        Dog dog = dogService.findDog(request.getDogId());
        if (!dog.getOwner().getId().equals(userId)) {
            throw new WaalException(ErrorCode.DOG_NOT_OWNED);
        }

        Kindergarten kindergarten = kindergartenService.findKindergarten(request.getKindergartenId());
        LocalDate date = request.getReservationDate();

        // 중복 예약 검사
        reservationRepository.findByDogIdAndKindergartenIdAndReservationDateAndStatusNot(
                dog.getId(), kindergarten.getId(), date, Reservation.Status.CANCELLED)
                .ifPresent(r -> { throw new WaalException(ErrorCode.RESERVATION_ALREADY_EXISTS); });

        // 정원 확인
        DailyCapacity capacity = dailyCapacityRepository.findByKindergartenIdAndDate(kindergarten.getId(), date)
                .orElseGet(() -> dailyCapacityRepository.save(DailyCapacity.builder()
                        .kindergarten(kindergarten)
                        .date(date)
                        .maxCapacity(kindergarten.getMaxCapacity())
                        .build()));

        if (capacity.isFull()) {
            throw new WaalException(ErrorCode.RESERVATION_CAPACITY_EXCEEDED);
        }

        capacity.incrementCount();

        Reservation reservation = reservationRepository.save(Reservation.builder()
                .dog(dog)
                .kindergarten(kindergarten)
                .reservationDate(date)
                .build());

        return ReservationResponse.from(reservation);
    }

    public List<ReservationResponse> getDogReservations(Long userId, Long dogId) {
        Dog dog = dogService.findDog(dogId);
        if (!dog.getOwner().getId().equals(userId)) {
            throw new WaalException(ErrorCode.DOG_NOT_OWNED);
        }
        return reservationRepository.findByDogIdOrderByReservationDateDesc(dogId).stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public List<ReservationResponse> getKindergartenReservations(Long userId, Long kindergartenId, LocalDate date) {
        kindergartenService.validateMember(userId, kindergartenId);
        return reservationRepository.findByKindergartenIdAndReservationDate(kindergartenId, date).stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional
    public ReservationResponse confirm(Long userId, Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        kindergartenService.validateMember(userId, reservation.getKindergarten().getId());
        if (reservation.getStatus() != Reservation.Status.PENDING) {
            throw new WaalException(ErrorCode.INVALID_RESERVATION_STATUS);
        }
        reservation.confirm();
        return ReservationResponse.from(reservation);
    }

    @Transactional
    public ReservationResponse cancel(Long userId, Long reservationId, ReservationCancelRequest request) {
        Reservation reservation = findReservation(reservationId);

        boolean isOwnerOfDog = reservation.getDog().getOwner().getId().equals(userId);
        boolean isKindergartenMember = kindergartenService.isMember(userId, reservation.getKindergarten().getId());

        if (!isOwnerOfDog && !isKindergartenMember) {
            throw new WaalException(ErrorCode.FORBIDDEN);
        }
        if (reservation.getStatus() == Reservation.Status.CANCELLED) {
            throw new WaalException(ErrorCode.INVALID_RESERVATION_STATUS);
        }

        // 정원 복원
        dailyCapacityRepository.findByKindergartenIdAndDate(
                reservation.getKindergarten().getId(), reservation.getReservationDate())
                .ifPresent(DailyCapacity::decrementCount);

        reservation.cancel(request.getReason());
        return ReservationResponse.from(reservation);
    }

    public DailyCapacityResponse getCapacity(Long kindergartenId, LocalDate date) {
        Kindergarten kindergarten = kindergartenService.findKindergarten(kindergartenId);
        return dailyCapacityRepository.findByKindergartenIdAndDate(kindergartenId, date)
                .map(DailyCapacityResponse::from)
                .orElseGet(() -> DailyCapacityResponse.empty(date, kindergarten.getMaxCapacity()));
    }

    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new WaalException(ErrorCode.RESERVATION_NOT_FOUND));
    }
}
