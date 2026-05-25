package com.ticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long>{

    Optional<Seat> findBySeatNumberAndShowId(String seatNumber, Long showId);

}
