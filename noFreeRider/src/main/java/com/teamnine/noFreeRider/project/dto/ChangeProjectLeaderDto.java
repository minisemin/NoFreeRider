package com.teamnine.noFreeRider.project.dto;

import java.util.UUID;

public record ChangeProjectLeaderDto(
        UUID exLeader_id,
        UUID newLeader_id
) {
}
