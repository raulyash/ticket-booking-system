package com.ticket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.service.SimulationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/simulate")
@RequiredArgsConstructor
public class SimulationController {

    private final SimulationService simulationService;

    @PostMapping
    public String simulate(){
        simulationService.simulateBooking();
        return "Simulation Started";
    }

}
