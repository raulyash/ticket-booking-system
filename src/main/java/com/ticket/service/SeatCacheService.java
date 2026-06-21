package com.ticket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.stereotype.Service;

import com.ticket.model.Seat;
import com.ticket.repository.SeatRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/* Class created for undestanding ReadWrite Locks */
@Service
@RequiredArgsConstructor
public class SeatCacheService {

    private final SeatRepository seatRepository;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final ConcurrentHashMap<String, Seat> seatCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void initializeCache(){

        System.out.println("Thread started :: " + Thread.currentThread().getName());
        readWriteLock.writeLock().lock();

        try{
            List<Seat> seats = seatRepository.findAll();

            seats.forEach(
                seat -> seatCache.put(seat.getSeatNumber(), seat)
            );

            System.out.println("LOADED : " + seatCache.size() + " seats in cache.");
        } finally {
            readWriteLock.writeLock().unlock();
            System.out.println("Thread finished :: " + Thread.currentThread().getName());
        }

    }

    public List<Seat>  getAllSeats(){
        readWriteLock.readLock().lock();
        try{
            return new ArrayList<>(seatCache.values());
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public Seat getSeat(String seatNumber){
        readWriteLock.readLock().lock();

        try{
            return seatCache.get(seatNumber);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void updateCache(Seat seat){
        readWriteLock.writeLock().lock();
        try{
            seatCache.put(seat.getSeatNumber(), seat);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

}
