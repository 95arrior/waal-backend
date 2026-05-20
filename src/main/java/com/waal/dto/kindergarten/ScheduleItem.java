package com.waal.dto.kindergarten;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleItem {
    private String dayOfWeek;
    private boolean isOpen;
    private String operatingStartTime;
    private String operatingEndTime;
}
