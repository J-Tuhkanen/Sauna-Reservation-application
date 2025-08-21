package com.sauna_reservation.sauna_reservation.controllers;

import java.util.ArrayList;
import java.util.Date;

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

        var result = _reservationRepository.getByWeekNumber(week);
        ArrayList<Reservation> r = new ArrayList<>();

        // Sort by day number into 7 x n dimensional arraylist
        for (Reservation reservation : result) {

            r.add(reservation);
        }

        return r;
    }

    @PostMapping()
    public Reservation postReservation(@RequestBody @Validated ReservationRequest request){

        return null;
    }
}
