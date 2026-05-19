package com.waal.service;

import com.waal.common.exception.ErrorCode;
import com.waal.common.exception.WaalException;
import com.waal.domain.Kindergarten;
import com.waal.domain.KindergartenMember;
import com.waal.domain.User;
import com.waal.dto.kindergarten.*;
import com.waal.repository.KindergartenMemberRepository;
import com.waal.repository.KindergartenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KindergartenService {

    private final KindergartenRepository kindergartenRepository;
    private final KindergartenMemberRepository memberRepository;
    private final UserService userService;

    @Transactional
    public KindergartenResponse create(Long userId, KindergartenCreateRequest request) {
        User owner = userService.findUser(userId);

        Kindergarten kindergarten = kindergartenRepository.save(Kindergarten.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .maxCapacity(request.getMaxCapacity())
                .build());

        memberRepository.save(KindergartenMember.builder()
                .user(owner)
                .kindergarten(kindergarten)
                .role(User.Role.OWNER)
                .build());

        return KindergartenResponse.from(kindergarten);
    }

    public KindergartenResponse get(Long kindergartenId) {
        return KindergartenResponse.from(findKindergarten(kindergartenId));
    }

    @Transactional
    public KindergartenResponse update(Long userId, Long kindergartenId, KindergartenUpdateRequest request) {
        validateOwner(userId, kindergartenId);
        Kindergarten kindergarten = findKindergarten(kindergartenId);
        kindergarten.update(request.getName(), request.getAddress(), request.getPhone(),
                request.getDescription(), request.getImageUrl(), request.getMaxCapacity());
        return KindergartenResponse.from(kindergarten);
    }

    public List<KindergartenMemberResponse> getMembers(Long userId, Long kindergartenId) {
        validateMember(userId, kindergartenId);
        return memberRepository.findByKindergartenId(kindergartenId).stream()
                .map(KindergartenMemberResponse::from)
                .toList();
    }

    public List<KindergartenResponse> getMyKindergartens(Long userId) {
        return memberRepository.findByUserId(userId).stream()
                .map(m -> KindergartenResponse.from(m.getKindergarten()))
                .toList();
    }

    public Kindergarten findKindergarten(Long id) {
        return kindergartenRepository.findById(id)
                .orElseThrow(() -> new WaalException(ErrorCode.KINDERGARTEN_NOT_FOUND));
    }

    public void validateOwner(Long userId, Long kindergartenId) {
        KindergartenMember member = memberRepository.findByUserIdAndKindergartenId(userId, kindergartenId)
                .orElseThrow(() -> new WaalException(ErrorCode.FORBIDDEN));
        if (member.getRole() != User.Role.OWNER) {
            throw new WaalException(ErrorCode.FORBIDDEN);
        }
    }

    public void validateMember(Long userId, Long kindergartenId) {
        if (!memberRepository.existsByUserIdAndKindergartenId(userId, kindergartenId)) {
            throw new WaalException(ErrorCode.FORBIDDEN);
        }
    }

    public boolean isMember(Long userId, Long kindergartenId) {
        return memberRepository.existsByUserIdAndKindergartenId(userId, kindergartenId);
    }
}
