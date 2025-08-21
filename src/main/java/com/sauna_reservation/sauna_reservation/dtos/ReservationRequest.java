package com.sauna_reservation.sauna_reservation.dtos;

import java.time.OffsetDateTime;

public record ReservationRequest(long userId, OffsetDateTime timestamp) {}
