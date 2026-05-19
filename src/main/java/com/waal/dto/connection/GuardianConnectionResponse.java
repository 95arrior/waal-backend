package com.waal.dto.connection;

import com.waal.domain.GuardianConnection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GuardianConnectionResponse {
    private Long id;
    private Long guardianId;
    private String guardianNickname;
    private Long kindergartenId;
    private String kindergartenName;
    private Long dogId;
    private String dogName;
    private String status;
    private LocalDateTime connectedAt;

    public static GuardianConnectionResponse from(GuardianConnection conn) {
        return GuardianConnectionResponse.builder()
                .id(conn.getId())
                .guardianId(conn.getGuardian().getId())
                .guardianNickname(conn.getGuardian().getNickname())
                .kindergartenId(conn.getKindergarten().getId())
                .kindergartenName(conn.getKindergarten().getName())
                .dogId(conn.getDog().getId())
                .dogName(conn.getDog().getName())
                .status(conn.getStatus().name())
                .connectedAt(conn.getConnectedAt())
                .build();
    }
}
