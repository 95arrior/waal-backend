package com.waal.controller;

import com.waal.common.response.ApiResponse;
import com.waal.dto.invite.InviteTokenCreateRequest;
import com.waal.dto.invite.InviteTokenResponse;
import com.waal.service.InviteTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "InviteToken", description = "초대 토큰 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InviteTokenController {

    private final InviteTokenService inviteTokenService;

    @Operation(summary = "초대 토큰 생성 (OWNER)")
    @PostMapping("/kindergartens/{kindergartenId}/invite-tokens")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<InviteTokenResponse> create(@AuthenticationPrincipal Long userId,
                                                   @PathVariable Long kindergartenId,
                                                   @Valid @RequestBody InviteTokenCreateRequest request) {
        return ApiResponse.ok(inviteTokenService.create(userId, kindergartenId, request));
    }

    @Operation(summary = "초대 토큰으로 유치원 가입")
    @PostMapping("/invite-tokens/{token}/accept")
    public ApiResponse<Void> accept(@AuthenticationPrincipal Long userId,
                                    @PathVariable String token) {
        inviteTokenService.accept(userId, token);
        return ApiResponse.ok("유치원에 성공적으로 가입되었습니다.");
    }
}
