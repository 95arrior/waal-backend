package com.waal.service;

import com.waal.common.exception.ErrorCode;
import com.waal.common.exception.WaalException;
import com.waal.domain.Dog;
import com.waal.domain.User;
import com.waal.dto.dog.DogCreateRequest;
import com.waal.dto.dog.DogResponse;
import com.waal.dto.dog.DogUpdateRequest;
import com.waal.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DogService {

    private final DogRepository dogRepository;
    private final UserService userService;

    @Transactional
    public DogResponse create(Long userId, DogCreateRequest request) {
        User owner = userService.findUser(userId);
        Dog dog = dogRepository.save(Dog.builder()
                .owner(owner)
                .name(request.getName())
                .breed(request.getBreed())
                .birthDate(request.getBirthDate())
                .weight(request.getWeight())
                .gender(request.getGender())
                .imageUrl(request.getImageUrl())
                .notes(request.getNotes())
                .build());
        return DogResponse.from(dog);
    }

    public List<DogResponse> getMyDogs(Long userId) {
        return dogRepository.findByOwnerId(userId).stream()
                .map(DogResponse::from)
                .toList();
    }

    public DogResponse get(Long userId, Long dogId) {
        Dog dog = findDog(dogId);
        validateOwnership(userId, dog);
        return DogResponse.from(dog);
    }

    @Transactional
    public DogResponse update(Long userId, Long dogId, DogUpdateRequest request) {
        Dog dog = findDog(dogId);
        validateOwnership(userId, dog);
        dog.update(request.getName(), request.getBreed(), request.getBirthDate(),
                request.getWeight(), request.getGender(), request.getImageUrl(), request.getNotes());
        return DogResponse.from(dog);
    }

    @Transactional
    public void delete(Long userId, Long dogId) {
        Dog dog = findDog(dogId);
        validateOwnership(userId, dog);
        dogRepository.delete(dog);
    }

    public Dog findDog(Long dogId) {
        return dogRepository.findById(dogId)
                .orElseThrow(() -> new WaalException(ErrorCode.DOG_NOT_FOUND));
    }

    private void validateOwnership(Long userId, Dog dog) {
        if (!dog.getOwner().getId().equals(userId)) {
            throw new WaalException(ErrorCode.DOG_NOT_OWNED);
        }
    }
}
