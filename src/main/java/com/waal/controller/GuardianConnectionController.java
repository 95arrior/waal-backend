package com.waal.controller;

import com.waal.common.response.ApiResponse;
import com.waal.domain.GuardianConnection;
import com.waal.dto.connection.GuardianConnectionRequest;
import com.waal.dto.connection.GuardianConnectionResponse;
import com.waal.service.GuardianConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "GuardianConnection", description = "보호자-유치원 연결 API")
@RestController
@RequiredArgsConstructor
public class GuardianConnectionController {

    private final GuardianConnectionService connectionService;

    @Operation(summary = "연결 요청 (보호자)")
    @PostMapping("/api/v1/kindergartens/{kindergartenId}/connections")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<GuardianConnectionResponse> request(@AuthenticationPrincipal Long userId,
                                                           @PathVariable Long kindergartenId,
                                                           @Valid @RequestBody GuardianConnectionRequest request) {
        return ApiResponse.ok(connectionService.requestConnection(userId, kindergartenId, request));
    }

    @Operation(summary = "유치원 연결 목록 (OWNER/TEACHER)")
    @GetMapping("/api/v1/kindergartens/{kindergartenId}/connections")
    public ApiResponse<List<GuardianConnectionResponse>> getKindergartenConnections(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long kindergartenId,
            @RequestParam(defaultValue = "PENDING") GuardianConnection.Status status) {
        return ApiResponse.ok(connectionService.getKindergartenConnections(userId, kindergartenId, status));
    }

    @Operation(summary = "내 연결 목록 (보호자)")
    @GetMapping("/api/v1/connections/me")
    public ApiResponse<List<GuardianConnectionResponse>> getMyConnections(@AuthenticationPrincipal Long userId) {
        return ApiResponse.ok(connectionService.getMyConnections(userId));
    }

    @Operation(summary = "연결 승인 (OWNER)")
    @PatchMapping("/api/v1/connections/{connectionId}/approve")
    public ApiResponse<GuardianConnectionResponse> approve(@AuthenticationPrincipal Long userId,
                                                           @PathVariable Long connectionId) {
        return ApiResponse.ok(connectionService.approve(userId, connectionId));
    }

    @Operation(summary = "연결 거절 (OWNER)")
    @PatchMapping("/api/v1/connections/{connectionId}/reject")
    public ApiResponse<GuardianConnectionResponse> reject(@AuthenticationPrincipal Long userId,
                                                          @PathVariable Long connectionId) {
        return ApiResponse.ok(connectionService.reject(userId, connectionId));
    }
}
