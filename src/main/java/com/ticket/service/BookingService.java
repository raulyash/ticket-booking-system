package com.ticket.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ticket.dto.BookingRequestDto;
import com.ticket.helper.CommonHelper;
import com.ticket.model.Booking;
import com.ticket.model.Seat;
import com.ticket.model.SeatStatus;
import com.ticket.repository.BookingRepository;
import com.ticket.repository.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    public String bookSeat(BookingRequestDto request){
        /* STEP 1: We need to check if the seat is available */
        Optional<Seat> seat = seatRepository.findBySeatNumberAndShowId(request.getSeatNumber(), request.getShowId());
        if(seat.isEmpty()){
            throw new RuntimeException("Seat not found for the given show");
        } else if(seat.get().getStatus() == SeatStatus.BOOKED){
            return "Seat is already booked";
        }

        /* added delay to check race condition */
        CommonHelper.addDelay();

        /* Step 2: If seat is available, book it */
        seat.get().setStatus(SeatStatus.BOOKED);
        seatRepository.save(seat.get());

        Booking booking = Booking.builder()
        .userName(request.getUserName())
        .seatNumber(request.getSeatNumber())
        .showId(request.getShowId())
        .bookingTime(LocalDateTime.now())
        .build();

        bookingRepository.save(booking);

        return "Seat booked successfully";
    }

}
