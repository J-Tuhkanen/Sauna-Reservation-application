package com.sauna_reservation.sauna_reservation.controllers;

import java.time.temporal.IsoFields;
import java.util.ArrayList;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sauna_reservation.sauna_reservation.dtos.ReservationRequest;
import com.sauna_reservation.sauna_reservation.models.Reservation;
import com.sauna_reservation.sauna_reservation.repositories.ReservationRepository;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationRepository _reservationRepository;

    public ReservationController(ReservationRepository reservationRepository){
        _reservationRepository = reservationRepository;
    }

    @GetMapping("{week}")
    @ResponseBody
    public ArrayList<Reservation> getReservations(@PathVariable int week){

        ArrayList<Reservation> r = new ArrayList<>();
        _reservationRepository.getByWeekNumber(week).iterator().forEachRemaining(r::add);

        return r;
    }

    @PostMapping()
    public Reservation postReservation(@RequestBody @Validated ReservationRequest request){

        int weekNumber = request.timestamp().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        
        // Try to find any reservations for given week, if any found, return null as no duplicate reservation should be made for each user.
        for(Reservation r :_reservationRepository.getByWeekNumber(weekNumber)){    
            if(r.userId == request.userId()){
                return null;
            }
        }

        // TODO: Add logic to check if the reservation slot is already taken.

        Reservation newReservation = new Reservation(request.userId(), request.timestamp().toInstant());
        _reservationRepository.save(newReservation);

        return newReservation;
    }
}
