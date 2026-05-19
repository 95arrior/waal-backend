package com.waal.dto.reservation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReservationCreateRequest {

    @NotNull(message = "반려견 ID는 필수입니다.")
    private Long dogId;

    @NotNull(message = "유치원 ID는 필수입니다.")
    private Long kindergartenId;

    @NotNull(message = "예약 날짜는 필수입니다.")
    @Future(message = "예약 날짜는 오늘 이후여야 합니다.")
    private LocalDate reservationDate;
}
