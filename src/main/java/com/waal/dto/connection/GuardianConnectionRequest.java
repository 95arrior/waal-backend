package com.waal.dto.connection;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuardianConnectionRequest {

    @NotNull(message = "반려견 ID는 필수입니다.")
    private Long dogId;
}
