package com.sauna_reservation.sauna_reservation.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.sauna_reservation.sauna_reservation.models.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    
    public Iterable<Reservation> getByWeekNumber(int weekNumber);
}
