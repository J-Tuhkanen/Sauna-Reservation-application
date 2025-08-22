package com.sauna_reservation.sauna_reservation.models;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reservation {
    
    private static final ZoneId ZONE = ZoneId.of("Europe/Helsinki"); // or ZoneOffset.UTC

    @Id    
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @JsonIgnore
    @Column(columnDefinition = "timestamptz", nullable = false)
    public Instant startsAt;
    
    public long userId; 
    public int weekNumber;
    
    public Reservation(long userId, Instant start) {
        startsAt = start;
    }

    public Reservation(){}

    @Transient
    public ZonedDateTime getReservationTime() {
        return startsAt.atZone(ZONE);
    }
}
