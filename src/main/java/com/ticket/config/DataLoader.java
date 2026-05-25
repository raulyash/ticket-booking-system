package com.ticket.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ticket.model.Seat;
import com.ticket.model.SeatStatus;
import com.ticket.model.Show;
import com.ticket.repository.SeatRepository;
import com.ticket.repository.ShowRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    @Override
    public void run(String... args) throws Exception {

        Show show = Show.builder()
        .movieName("Avenger")
        .theaterName("PVR").build();

        Show savedShow = showRepository.save(show);

        for(int i=1; i<=10; i++) {
            Seat seat = Seat.builder()
            .seatNumber("A"+i)
            .status(SeatStatus.AVAILABLE)
            .showId(savedShow.getId())
            .build();

            seatRepository.save(seat);
        }

        System.out.println("Data loaded successfully!");
    }

}
