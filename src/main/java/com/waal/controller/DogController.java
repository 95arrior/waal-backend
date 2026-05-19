package com.waal.controller;

import com.waal.common.response.ApiResponse;
import com.waal.dto.dog.DogCreateRequest;
import com.waal.dto.dog.DogResponse;
import com.waal.dto.dog.DogUpdateRequest;
import com.waal.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Dog", description = "반려견 API")
@RestController
@RequestMapping("/api/v1/dogs")
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;

    @Operation(summary = "반려견 등록")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DogResponse> create(@AuthenticationPrincipal Long userId,
                                           @Valid @RequestBody DogCreateRequest request) {
        return ApiResponse.ok(dogService.create(userId, request));
    }

    @Operation(summary = "내 반려견 목록")
    @GetMapping
    public ApiResponse<List<DogResponse>> getMyDogs(@AuthenticationPrincipal Long userId) {
        return ApiResponse.ok(dogService.getMyDogs(userId));
    }

    @Operation(summary = "반려견 상세 조회")
    @GetMapping("/{dogId}")
    public ApiResponse<DogResponse> get(@AuthenticationPrincipal Long userId, @PathVariable Long dogId) {
        return ApiResponse.ok(dogService.get(userId, dogId));
    }

    @Operation(summary = "반려견 정보 수정")
    @PatchMapping("/{dogId}")
    public ApiResponse<DogResponse> update(@AuthenticationPrincipal Long userId,
                                           @PathVariable Long dogId,
                                           @Valid @RequestBody DogUpdateRequest request) {
        return ApiResponse.ok(dogService.update(userId, dogId, request));
    }

    @Operation(summary = "반려견 삭제")
    @DeleteMapping("/{dogId}")
    public ApiResponse<Void> delete(@AuthenticationPrincipal Long userId, @PathVariable Long dogId) {
        dogService.delete(userId, dogId);
        return ApiResponse.ok("반려견이 삭제되었습니다.");
    }
}
