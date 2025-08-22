package com.sauna_reservation.sauna_reservation;

import java.time.Instant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sauna_reservation.sauna_reservation.models.Reservation;
import com.sauna_reservation.sauna_reservation.repositories.ReservationRepository;

@SpringBootApplication()
public class SaunaReservationApplication {
	
	public static void main(String[] args) {    
		SpringApplication.run(SaunaReservationApplication.class, args);    
	}

	@Bean
	public CommandLineRunner clr(ReservationRepository repo){

		return (args) -> {

			var now = Instant.now();
			var reservation = new Reservation(0, now);
			
			repo.save(reservation);
		};
	}
}
