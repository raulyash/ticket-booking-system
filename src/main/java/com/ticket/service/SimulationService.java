package com.ticket.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.ticket.dto.BookingRequestDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimulationService {

    private final BookingService bookingService;


    public void simulateBooking(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for(int i=0; i<=100; i++){
            int userNumber = i;
            executorService.submit(()->{
                BookingRequestDto bookingRequestDto = BookingRequestDto.builder()
                .showId(1L)
                .seatNumber("A" + ((userNumber % 5) + 1))
                .userName("User-" + userNumber).build();

                String response = bookingService.bookSeat(bookingRequestDto);

                System.out.println(Thread.currentThread().getName() + "->" + response);
            });
        }

        executorService.shutdown();

    }




}
