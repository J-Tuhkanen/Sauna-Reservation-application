package com.sauna_reservation.sauna_reservation.controllers;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> postReservation(@RequestBody @Validated ReservationRequest request) throws Exception{

        int weekNumber = request.timestamp().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        OffsetDateTime truncatedHour = request.timestamp().truncatedTo(ChronoUnit.HOURS);

        if(request.timestamp().equals(truncatedHour) == false){

            return ResponseEntity.badRequest().body("Invalid reservation data.");
        }

        // Try to find any reservations for given week, if any found, return null as no duplicate reservation should be made for each user.
        for(Reservation r :_reservationRepository.getByWeekNumber(weekNumber)){    
            if(r.userId == request.userId()){
                return null;
                // TODO: Validate that there is no overlapping reservation.
            }
        }

        // TODO: Add logic to check if the reservation slot is already taken.

        Reservation newReservation = new Reservation(request.userId(), truncatedHour.toInstant());
        _reservationRepository.save(newReservation);

        return ResponseEntity.ok().body(newReservation);
    }
}
