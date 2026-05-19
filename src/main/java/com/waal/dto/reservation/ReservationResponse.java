package com.waal.dto.reservation;

import com.waal.domain.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationResponse {
    private Long id;
    private Long dogId;
    private String dogName;
    private Long kindergartenId;
    private String kindergartenName;
    private LocalDate reservationDate;
    private String status;
    private String cancelReason;
    private LocalDateTime createdAt;

    public static ReservationResponse from(Reservation r) {
        return ReservationResponse.builder()
                .id(r.getId())
                .dogId(r.getDog().getId())
                .dogName(r.getDog().getName())
                .kindergartenId(r.getKindergarten().getId())
                .kindergartenName(r.getKindergarten().getName())
                .reservationDate(r.getReservationDate())
                .status(r.getStatus().name())
                .cancelReason(r.getCancelReason())
                .createdAt(r.getCreatedAt())
                .build();
    }
}
