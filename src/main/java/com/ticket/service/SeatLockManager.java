package com.ticket.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

@Component
public class SeatLockManager {

    private final ConcurrentHashMap<String, ReentrantLock> seatLocks = new ConcurrentHashMap<>();

    public ReentrantLock getLock(String seatNumber){
        return seatLocks.computeIfAbsent(seatNumber, key -> new ReentrantLock());
    }

}
