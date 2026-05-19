package com.waal.dto.dog;

import com.waal.domain.Dog;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DogUpdateRequest {

    @NotBlank(message = "반려견 이름은 필수입니다.")
    private String name;

    private String breed;
    private LocalDate birthDate;
    private Double weight;

    @NotNull(message = "성별은 필수입니다.")
    private Dog.Gender gender;

    private String imageUrl;
    private String notes;
}
