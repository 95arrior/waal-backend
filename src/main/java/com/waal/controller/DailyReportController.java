package com.waal.controller;

import com.waal.common.response.ApiResponse;
import com.waal.dto.report.DailyReportCreateRequest;
import com.waal.dto.report.DailyReportResponse;
import com.waal.dto.report.DailyReportUpdateRequest;
import com.waal.service.DailyReportService;
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

@Tag(name = "DailyReport", description = "일일 보고서 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DailyReportController {

    private final DailyReportService dailyReportService;

    @Operation(summary = "일일 보고서 작성 (OWNER/TEACHER)")
    @PostMapping("/daily-reports")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DailyReportResponse> create(@AuthenticationPrincipal Long userId,
                                                   @Valid @RequestBody DailyReportCreateRequest request) {
        return ApiResponse.ok(dailyReportService.create(userId, request));
    }

    @Operation(summary = "보고서 상세 조회")
    @GetMapping("/daily-reports/{reportId}")
    public ApiResponse<DailyReportResponse> get(@AuthenticationPrincipal Long userId,
                                                @PathVariable Long reportId) {
        return ApiResponse.ok(dailyReportService.get(userId, reportId));
    }

    @Operation(summary = "반려견 보고서 목록 (보호자)")
    @GetMapping("/dogs/{dogId}/daily-reports")
    public ApiResponse<List<DailyReportResponse>> getDogReports(@AuthenticationPrincipal Long userId,
                                                                @PathVariable Long dogId) {
        return ApiResponse.ok(dailyReportService.getDogReports(userId, dogId));
    }

    @Operation(summary = "유치원 날짜별 보고서 목록 (OWNER/TEACHER)")
    @GetMapping("/kindergartens/{kindergartenId}/daily-reports")
    public ApiResponse<List<DailyReportResponse>> getKindergartenReports(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long kindergartenId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ApiResponse.ok(dailyReportService.getKindergartenReports(userId, kindergartenId, date));
    }

    @Operation(summary = "보고서 수정 (OWNER/TEACHER)")
    @PatchMapping("/daily-reports/{reportId}")
    public ApiResponse<DailyReportResponse> update(@AuthenticationPrincipal Long userId,
                                                   @PathVariable Long reportId,
                                                   @Valid @RequestBody DailyReportUpdateRequest request) {
        return ApiResponse.ok(dailyReportService.update(userId, reportId, request));
    }
}
