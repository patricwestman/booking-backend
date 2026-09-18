package com.pensionat.config;

import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.model.RoomType;
import com.pensionat.room.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedRooms(RoomRepository roomRepository) {
        return args -> {
            long roomCount = roomRepository.count();

            if (roomCount > 0) {
                System.out.println("Database already contains " + roomCount + " - skipping seed.");
                return;
            }

            System.out.println("Database Empty - Seeding Rooms: ");

            roomRepository.save(buildRoom(101, 1, RoomType.SINGLE, 800, "Single-bed room with a modernstyle on a marine take -  north side of building", "https://images.unsplash.com/photo-1496417263034-38ec4f0b665a?w=1280&q=80"));
            roomRepository.save(buildRoom(102, 1, RoomType.SINGLE, 800, "Single-bed room with a modernstyle on a marine take -  south side of building", "https://images.unsplash.com/photo-1496417263034-38ec4f0b665a?w=1280&q=80"));
            roomRepository.save(buildRoom(103, 1, RoomType.SINGLE, 800, "Single-bed room with a modernstyle on a marine take -  west side of building", "https://images.unsplash.com/photo-1496417263034-38ec4f0b665a?w=1280&q=80"));
            roomRepository.save(buildRoom(104, 1, RoomType.SINGLE, 800, "Single-bed room with a modernstyle on a marine take -  east side of building", "https://images.unsplash.com/photo-1496417263034-38ec4f0b665a?w=1280&q=80"));
            roomRepository.save(buildRoom(105, 1, RoomType.SINGLE, 900, "Single-bed in a minmalistic room with a bigger bed for better sleep", "https://images.unsplash.com/photo-1759264244726-adde4e4318fc?w=1280&q=80"));

            roomRepository.save(buildRoom(201, 1, RoomType.DOUBLE, 1200, "Queensize rustic with wooden features OBS no windows - north side of building", "https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=1280&q=80"));
            roomRepository.save(buildRoom(202, 1, RoomType.DOUBLE, 1200, "Queensize rustic with wooden features OBS no windows - east side of building", "https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=1280&q=80"));
            roomRepository.save(buildRoom(203, 2, RoomType.DOUBLE, 1200, "Doubleroom with a extra bed for family or friends, modern and simple with a great view", "https://images.unsplash.com/photo-1776500587913-6e55907a738e?w=1280&q=80"));
            roomRepository.save(buildRoom(204, 1, RoomType.DOUBLE, 1500, "Kingbed minimalistic with a beautiful view", "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=1280&q=80"));
            roomRepository.save(buildRoom(205, 1, RoomType.DOUBLE, 2000, "Rustic relaxation room for two with outdoor seating", "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=1280&q=80"));

            System.out.println("Done - 10 rooms seeded.");
        };
    }

    private RoomEntity buildRoom(int roomNumber, int beds, RoomType roomType, int pricePerNight, String description, String photoUrl) {
        RoomEntity room = new RoomEntity();
        room.setRoomNumber(roomNumber);
        room.setRoomType(roomType);
        room.setBeds(beds);
        room.setPricePerNight(pricePerNight);
        room.setDescription(description);
        room.setPhotoUrl(photoUrl);
        return room;
    }
}