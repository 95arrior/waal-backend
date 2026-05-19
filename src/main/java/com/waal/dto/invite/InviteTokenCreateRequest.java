package com.waal.dto.invite;

import com.waal.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InviteTokenCreateRequest {

    @NotNull(message = "초대할 역할은 필수입니다.")
    private User.Role targetRole;
}
