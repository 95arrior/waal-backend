package com.waal.controller;

import com.waal.common.response.ApiResponse;
import com.waal.dto.reservation.DailyCapacityResponse;
import com.waal.dto.reservation.ReservationCancelRequest;
import com.waal.dto.reservation.ReservationCreateRequest;
import com.waal.dto.reservation.ReservationResponse;
import com.waal.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Reservation", description = "예약 API")
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "예약 생성")
    @PostMapping("/api/v1/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReservationResponse> create(@AuthenticationPrincipal Long userId,
                                                   @Valid @RequestBody ReservationCreateRequest request) {
        return ApiResponse.ok(reservationService.create(userId, request));
    }

    @Operation(summary = "반려견 예약 목록")
    @GetMapping("/api/v1/dogs/{dogId}/reservations")
    public ApiResponse<List<ReservationResponse>> getDogReservations(@AuthenticationPrincipal Long userId,
                                                                      @PathVariable Long dogId) {
        return ApiResponse.ok(reservationService.getDogReservations(userId, dogId));
    }

    @Operation(summary = "유치원 날짜별 예약 목록 (OWNER/TEACHER)")
    @GetMapping("/api/v1/kindergartens/{kindergartenId}/reservations")
    public ApiResponse<List<ReservationResponse>> getKindergartenReservations(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long kindergartenId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ApiResponse.ok(reservationService.getKindergartenReservations(userId, kindergartenId, date));
    }

    @Operation(summary = "유치원 날짜별 정원 조회")
    @GetMapping("/api/v1/kindergartens/{kindergartenId}/capacity")
    public ApiResponse<DailyCapacityResponse> getCapacity(@PathVariable Long kindergartenId,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ApiResponse.ok(reservationService.getCapacity(kindergartenId, date));
    }

    @Operation(summary = "예약 확정 (OWNER/TEACHER)")
    @PatchMapping("/api/v1/reservations/{reservationId}/confirm")
    public ApiResponse<ReservationResponse> confirm(@AuthenticationPrincipal Long userId,
                                                    @PathVariable Long reservationId) {
        return ApiResponse.ok(reservationService.confirm(userId, reservationId));
    }

    @Operation(summary = "예약 취소")
    @PatchMapping("/api/v1/reservations/{reservationId}/cancel")
    public ApiResponse<ReservationResponse> cancel(@AuthenticationPrincipal Long userId,
                                                   @PathVariable Long reservationId,
                                                   @RequestBody ReservationCancelRequest request) {
        return ApiResponse.ok(reservationService.cancel(userId, reservationId, request));
    }
}
