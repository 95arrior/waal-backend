package com.waal.service;

import com.waal.common.exception.ErrorCode;
import com.waal.common.exception.WaalException;
import com.waal.domain.Dog;
import com.waal.domain.GuardianConnection;
import com.waal.domain.Kindergarten;
import com.waal.domain.User;
import com.waal.dto.connection.GuardianConnectionRequest;
import com.waal.dto.connection.GuardianConnectionResponse;
import com.waal.repository.GuardianConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuardianConnectionService {

    private final GuardianConnectionRepository connectionRepository;
    private final UserService userService;
    private final KindergartenService kindergartenService;
    private final DogService dogService;

    @Transactional
    public GuardianConnectionResponse requestConnection(Long userId, Long kindergartenId,
                                                        GuardianConnectionRequest request) {
        User guardian = userService.findUser(userId);
        Kindergarten kindergarten = kindergartenService.findKindergarten(kindergartenId);
        Dog dog = dogService.findDog(request.getDogId());

        if (!dog.getOwner().getId().equals(userId)) {
            throw new WaalException(ErrorCode.DOG_NOT_OWNED);
        }
        if (connectionRepository.existsByGuardianIdAndKindergartenIdAndDogId(userId, kindergartenId, dog.getId())) {
            throw new WaalException(ErrorCode.ALREADY_CONNECTED);
        }

        GuardianConnection connection = connectionRepository.save(GuardianConnection.builder()
                .guardian(guardian)
                .kindergarten(kindergarten)
                .dog(dog)
                .build());
        return GuardianConnectionResponse.from(connection);
    }

    public List<GuardianConnectionResponse> getKindergartenConnections(Long userId, Long kindergartenId,
                                                                        GuardianConnection.Status status) {
        kindergartenService.validateMember(userId, kindergartenId);
        return connectionRepository.findByKindergartenIdAndStatus(kindergartenId, status).stream()
                .map(GuardianConnectionResponse::from)
                .toList();
    }

    public List<GuardianConnectionResponse> getMyConnections(Long userId) {
        return connectionRepository.findByGuardianIdAndStatus(userId, GuardianConnection.Status.ACTIVE).stream()
                .map(GuardianConnectionResponse::from)
                .toList();
    }

    @Transactional
    public GuardianConnectionResponse approve(Long userId, Long connectionId) {
        GuardianConnection connection = findConnection(connectionId);
        kindergartenService.validateOwner(userId, connection.getKindergarten().getId());
        connection.activate();
        return GuardianConnectionResponse.from(connection);
    }

    @Transactional
    public GuardianConnectionResponse reject(Long userId, Long connectionId) {
        GuardianConnection connection = findConnection(connectionId);
        kindergartenService.validateOwner(userId, connection.getKindergarten().getId());
        connection.deactivate();
        return GuardianConnectionResponse.from(connection);
    }

    private GuardianConnection findConnection(Long connectionId) {
        return connectionRepository.findById(connectionId)
                .orElseThrow(() -> new WaalException(ErrorCode.GUARDIAN_CONNECTION_NOT_FOUND));
    }
}
