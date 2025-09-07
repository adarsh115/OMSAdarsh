package com.adarsh.oms.oms_events.events;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class RiskApprovedEvent {
    private Long orderId;
    private boolean approved;
    private String reason;
    private Instant timestamp;
}
