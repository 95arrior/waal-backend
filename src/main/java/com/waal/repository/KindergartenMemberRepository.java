package com.waal.repository;

import com.waal.domain.KindergartenMember;
import com.waal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KindergartenMemberRepository extends JpaRepository<KindergartenMember, Long> {
    Optional<KindergartenMember> findByUserIdAndKindergartenId(Long userId, Long kindergartenId);
    List<KindergartenMember> findByKindergartenId(Long kindergartenId);
    List<KindergartenMember> findByUserId(Long userId);
    boolean existsByUserIdAndKindergartenId(Long userId, Long kindergartenId);
    List<KindergartenMember> findByKindergartenIdAndRole(Long kindergartenId, User.Role role);
}
