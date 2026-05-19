package com.waal.dto.kindergarten;

import com.waal.domain.Kindergarten;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class KindergartenResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String description;
    private String imageUrl;
    private int maxCapacity;
    private LocalDateTime createdAt;

    public static KindergartenResponse from(Kindergarten k) {
        return KindergartenResponse.builder()
                .id(k.getId())
                .name(k.getName())
                .address(k.getAddress())
                .phone(k.getPhone())
                .description(k.getDescription())
                .imageUrl(k.getImageUrl())
                .maxCapacity(k.getMaxCapacity())
                .createdAt(k.getCreatedAt())
                .build();
    }
}
