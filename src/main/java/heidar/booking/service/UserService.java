package heidar.booking.service;

import heidar.booking.model.User;
import heidar.booking.repo.ReservationRepo;
import heidar.booking.repo.RoomRepo;
import heidar.booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private BCryptPasswordEncoder encrypt;


    public String enCryptedPassword(User user) {
        return encrypt.encode(user.getPassword());
    }


    public void save(User user) {
        userRepo.save(user);
    }


    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
