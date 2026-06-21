package com.ticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.ticket.model.Seat;

import jakarta.persistence.LockModeType;

public interface SeatRepository extends JpaRepository<Seat, Long>{

    Optional<Seat> findBySeatNumberAndShowId(String seatNumber, Long showId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT s
            FROM Seat s
            WHERE s.seatNumber = :seatNumber
            AND s.showId = :showId
            """)
    Optional<Seat> findSeatForUpdate(String seatNumber, Long showId);

}
