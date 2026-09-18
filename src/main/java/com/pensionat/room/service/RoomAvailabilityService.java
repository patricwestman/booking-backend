package com.pensionat.room.service;

import com.pensionat.booking.model.BookingStatus;
import com.pensionat.booking.repository.BookingRepository;
import com.pensionat.exception.BadRequestException;
import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomAvailabilityService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public RoomAvailabilityService(RoomRepository roomRepository,
                                   BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<RoomEntity> findAvailableRooms(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new BadRequestException("Start and end dates are required");
        }
        if (!endDate.isAfter(startDate)) {
            throw new BadRequestException("End date must be after start date");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("Start date cannot be in the past");
        }

        return roomRepository.findAll().stream()
                .filter(room -> !bookingRepository
                        .existsByRoomIdAndBookingStatusAndStartDateBeforeAndEndDateAfter(
                                room.getId(),
                                BookingStatus.ACTIVE,
                                endDate,
                                startDate
                        ))
                .toList();
    }
}