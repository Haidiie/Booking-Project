package heidar.booking.service;

import heidar.booking.repo.ReservationRepo;
import heidar.booking.repo.RoomRepo;
import heidar.booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private ReservationRepo reservationRepo;


}
