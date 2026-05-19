package com.waal.dto.dog;

import com.waal.domain.Dog;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class DogResponse {
    private Long id;
    private Long ownerId;
    private String ownerNickname;
    private String name;
    private String breed;
    private LocalDate birthDate;
    private Double weight;
    private String gender;
    private String imageUrl;
    private String notes;
    private LocalDateTime createdAt;

    public static DogResponse from(Dog dog) {
        return DogResponse.builder()
                .id(dog.getId())
                .ownerId(dog.getOwner().getId())
                .ownerNickname(dog.getOwner().getNickname())
                .name(dog.getName())
                .breed(dog.getBreed())
                .birthDate(dog.getBirthDate())
                .weight(dog.getWeight())
                .gender(dog.getGender() != null ? dog.getGender().name() : null)
                .imageUrl(dog.getImageUrl())
                .notes(dog.getNotes())
                .createdAt(dog.getCreatedAt())
                .build();
    }
}
