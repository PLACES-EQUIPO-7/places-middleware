package com.example.me.DTOs.place;

import com.example.me.utils.enums.ShipmentStatus;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeStatusDTO {

    private Long shipmentId;

    private ShipmentStatus newStatus;

    private OffsetDateTime deliveredAt;
}
