package com.waal.service;

import com.waal.common.exception.ErrorCode;
import com.waal.common.exception.WaalException;
import com.waal.domain.*;
import com.waal.dto.invite.InviteTokenCreateRequest;
import com.waal.dto.invite.InviteTokenResponse;
import com.waal.repository.InviteTokenRepository;
import com.waal.repository.KindergartenMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InviteTokenService {

    private final InviteTokenRepository inviteTokenRepository;
    private final KindergartenMemberRepository memberRepository;
    private final KindergartenService kindergartenService;
    private final UserService userService;

    @Transactional
    public InviteTokenResponse create(Long userId, Long kindergartenId, InviteTokenCreateRequest request) {
        kindergartenService.validateOwner(userId, kindergartenId);
        User creator = userService.findUser(userId);
        Kindergarten kindergarten = kindergartenService.findKindergarten(kindergartenId);

        InviteToken token = inviteTokenRepository.save(InviteToken.builder()
                .kindergarten(kindergarten)
                .token(UUID.randomUUID().toString())
                .targetRole(request.getTargetRole())
                .createdBy(creator)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build());

        return InviteTokenResponse.from(token);
    }

    @Transactional
    public void accept(Long userId, String tokenValue) {
        InviteToken inviteToken = inviteTokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new WaalException(ErrorCode.INVITE_TOKEN_NOT_FOUND));

        if (inviteToken.isUsed()) {
            throw new WaalException(ErrorCode.INVITE_TOKEN_ALREADY_USED);
        }
        if (inviteToken.isExpired()) {
            throw new WaalException(ErrorCode.INVITE_TOKEN_EXPIRED);
        }

        User user = userService.findUser(userId);
        Kindergarten kindergarten = inviteToken.getKindergarten();

        if (memberRepository.existsByUserIdAndKindergartenId(userId, kindergarten.getId())) {
            throw new WaalException(ErrorCode.ALREADY_KINDERGARTEN_MEMBER);
        }

        memberRepository.save(KindergartenMember.builder()
                .user(user)
                .kindergarten(kindergarten)
                .role(inviteToken.getTargetRole())
                .build());

        inviteToken.markAsUsed();
    }
}
