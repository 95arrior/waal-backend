package com.waal.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kindergartens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Kindergarten extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    @Column(nullable = false)
    private int maxCapacity;

    public void update(String name, String address, String phone, String description, String imageUrl, int maxCapacity) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.imageUrl = imageUrl;
        this.maxCapacity = maxCapacity;
    }
}
