package com.pensionat.room.dto;

import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.model.RoomType;

public record RoomResponse(
        Long id,
        int roomNumber,
        RoomType roomType,
        int beds,
        int pricePerNight,
        String description,
        String photoUrl
) {
    public static RoomResponse from(RoomEntity entity) {
        return new RoomResponse(
                entity.getId(),
                entity.getRoomNumber(),
                entity.getRoomType(),
                entity.getBeds(),
                entity.getPricePerNight(),
                entity.getDescription(),
                entity.getPhotoUrl()
        );
    }
}