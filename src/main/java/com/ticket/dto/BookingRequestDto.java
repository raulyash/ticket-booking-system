package com.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingRequestDto {

    @NotBlank
    private String userName, seatNumber;

    @NotNull
    private Long showId;

}
