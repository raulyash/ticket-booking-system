package com.ticket.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.ticket.dto.BookingRequestDto;
import com.ticket.helper.CommonHelper;
import com.ticket.model.Booking;
import com.ticket.model.Seat;
import com.ticket.model.SeatStatus;
import com.ticket.repository.BookingRepository;
import com.ticket.repository.SeatRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    /* Commenting this for Pessimistic Lock implementation 
    private final SeatLockManager lockManager;*/

    // private final ReentrantLock lock = new ReentrantLock(); 

    private final SeatCacheService seatCacheService;


    @Transactional
    public String bookSeat(BookingRequestDto request) throws InterruptedException {
        // ReentrantLock lock = lockManager.getLock(request.getSeatNumber());
        // lock.lock();
        try {
            /* STEP 1: We need to check if the seat is available */
            System.out.println(Thread.currentThread().getName() + " TRYING TO ACQUIRED DB LOCK FOR " + request.getSeatNumber());
            Optional<Seat> seat = seatRepository.findSeatForUpdate(request.getSeatNumber(),
                    request.getShowId());
            System.out.println(Thread.currentThread().getName() + " DB LOCK ACQUIRED FOR " + request.getSeatNumber());
            /* added delay to check race condition */
            CommonHelper.addDelay();
            if (seat.isEmpty()) {
                throw new RuntimeException("Seat not found for the given show");
            } else if (seat.get().getStatus() == SeatStatus.BOOKED) {
                return "Seat is already booked";
            }

            /* Step 2: If seat is available, book it */
            seat.get().setStatus(SeatStatus.BOOKED);

            // try{
            seatRepository.save(seat.get());
            // } catch (ObjectOptimisticLockingFailureException e){
            //     return "Seat already booked by another user";
            // }

            // updating cache
            seatCacheService.updateCache(seat.get());

            Booking booking = Booking.builder()
                    .userName(request.getUserName())
                    .seatNumber(request.getSeatNumber())
                    .showId(request.getShowId())
                    .bookingTime(LocalDateTime.now())
                    .build();

            bookingRepository.save(booking);

            // lock.unlock();
            System.out.println(Thread.currentThread().getName() + " RELEASING LOCK " + request.getSeatNumber());
            return "Seat booked successfully";
        } finally {
            System.out.println(Thread.currentThread().getName() + " RELEASING LOCK FOR " + request.getSeatNumber());
            // lock.unlock();
        }
    }

}
