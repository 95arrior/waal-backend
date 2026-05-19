package com.waal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "dogs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Dog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String name;

    private String breed;

    private LocalDate birthDate;

    private Double weight;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public void update(String name, String breed, LocalDate birthDate, Double weight, Gender gender, String imageUrl, String notes) {
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
        this.weight = weight;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.notes = notes;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
