package com.waal.controller;

import com.waal.common.response.ApiResponse;
import com.waal.dto.kindergarten.*;
import com.waal.service.KindergartenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Kindergarten", description = "유치원 API")
@RestController
@RequestMapping("/api/v1/kindergartens")
@RequiredArgsConstructor
public class KindergartenController {

    private final KindergartenService kindergartenService;

    @Operation(summary = "유치원 등록 (OWNER)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<KindergartenResponse> create(@AuthenticationPrincipal Long userId,
                                                    @Valid @RequestBody KindergartenCreateRequest request) {
        return ApiResponse.ok(kindergartenService.create(userId, request));
    }

    @Operation(summary = "유치원 상세 조회")
    @GetMapping("/{kindergartenId}")
    public ApiResponse<KindergartenResponse> get(@PathVariable Long kindergartenId) {
        return ApiResponse.ok(kindergartenService.get(kindergartenId));
    }

    @Operation(summary = "유치원 수정 (OWNER)")
    @PatchMapping("/{kindergartenId}")
    public ApiResponse<KindergartenResponse> update(@AuthenticationPrincipal Long userId,
                                                    @PathVariable Long kindergartenId,
                                                    @Valid @RequestBody KindergartenUpdateRequest request) {
        return ApiResponse.ok(kindergartenService.update(userId, kindergartenId, request));
    }

    @Operation(summary = "유치원 멤버 목록 (OWNER/TEACHER)")
    @GetMapping("/{kindergartenId}/members")
    public ApiResponse<List<KindergartenMemberResponse>> getMembers(@AuthenticationPrincipal Long userId,
                                                                    @PathVariable Long kindergartenId) {
        return ApiResponse.ok(kindergartenService.getMembers(userId, kindergartenId));
    }

    @Operation(summary = "내 소속 유치원 목록")
    @GetMapping("/me")
    public ApiResponse<List<KindergartenResponse>> getMyKindergartens(@AuthenticationPrincipal Long userId) {
        return ApiResponse.ok(kindergartenService.getMyKindergartens(userId));
    }
}
