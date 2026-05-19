package com.waal.dto.invite;

import com.waal.domain.InviteToken;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class InviteTokenResponse {
    private Long id;
    private String token;
    private String targetRole;
    private Long kindergartenId;
    private String kindergartenName;
    private LocalDateTime expiresAt;
    private boolean used;

    public static InviteTokenResponse from(InviteToken inviteToken) {
        return InviteTokenResponse.builder()
                .id(inviteToken.getId())
                .token(inviteToken.getToken())
                .targetRole(inviteToken.getTargetRole().name())
                .kindergartenId(inviteToken.getKindergarten().getId())
                .kindergartenName(inviteToken.getKindergarten().getName())
                .expiresAt(inviteToken.getExpiresAt())
                .used(inviteToken.isUsed())
                .build();
    }
}
