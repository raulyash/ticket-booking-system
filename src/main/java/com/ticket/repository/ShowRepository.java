package com.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.Show;

public interface ShowRepository extends JpaRepository<Show, Long>{

}
