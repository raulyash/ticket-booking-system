package com.ticket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.model.Seat;
import com.ticket.service.SeatCacheService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/seats")
@RestController
@RequiredArgsConstructor
public class SeatController {

    private final SeatCacheService seatCacheService;

    @GetMapping
    public List<Seat> getSeats(){
        return seatCacheService.getAllSeats();
    }
}
