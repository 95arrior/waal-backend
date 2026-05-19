package com.waal.dto.kindergarten;

import com.waal.domain.KindergartenMember;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class KindergartenMemberResponse {
    private Long id;
    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private String role;
    private LocalDateTime joinedAt;

    public static KindergartenMemberResponse from(KindergartenMember member) {
        return KindergartenMemberResponse.builder()
                .id(member.getId())
                .userId(member.getUser().getId())
                .nickname(member.getUser().getNickname())
                .profileImageUrl(member.getUser().getProfileImageUrl())
                .role(member.getRole().name())
                .joinedAt(member.getJoinedAt())
                .build();
    }
}
