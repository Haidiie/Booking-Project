package heidar.booking.service;

import heidar.booking.model.Reservation;
import heidar.booking.model.User;
import heidar.booking.repo.ReservationRepo;
import heidar.booking.repo.RoomRepo;
import heidar.booking.repo.UserRepo;
import heidar.booking.temp.CurrentReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;


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

    @Transactional
    public Collection<Reservation> getReservationsForLoggedUser() {
        return reservationRepo.findAllByUserId((getLoggedUserId()));
    }

    @Transactional
    public void deleteReservation(int resId) {

        reservationRepo.deleteById(resId);
    }

    @Transactional
    public void saveOrUpdateReservation(CurrentReservation currentReservation) {
        Reservation reservation = new Reservation();
        reservation.setUserId(getLoggedUserId());
        reservation.setArrivalDate(currentReservation.getArrivalDate());
        reservation.setOpenBuffet(currentReservation.getOpenBuffet());
        reservation.setStayDays(currentReservation.getStayPeriod());
        reservation.setChildren(currentReservation.getChildren());
        reservation.setPersons(currentReservation.getPersons());
        reservation.setPrice(currentReservation.getPrice());
        reservation.setRooms(currentReservation.getRooms());
        reservation.setRoom(currentReservation.getRoom());
        reservation.setId(currentReservation.getId());

        reservationRepo.save(reservation);

    }

    public CurrentReservation reservationToCurrentReservationById(int resId) {
        Reservation reservation = getReservationForLoggedUserById(resId);
        CurrentReservation currentReservation = new CurrentReservation();

        currentReservation.setArrivalDate(reservation.getArrivalDate());
        currentReservation.setOpenBuffet(reservation.getOpenBuffet());
        currentReservation.setStayPeriod(reservation.getStayDays());
        currentReservation.setChildren(reservation.getChildren());
        currentReservation.setPersons(reservation.getPersons());
        currentReservation.setUsertId(reservation.getUserId());
        currentReservation.setRooms(reservation.getRooms());
        currentReservation.setPrice(reservation.getPrice());
        currentReservation.setRoom(reservation.getRoom());
        currentReservation.setId(reservation.getId());

        return currentReservation;
    }

    @Transactional
    public Reservation getReservationForLoggedUserById(int resId) {

        return reservationRepo.findById(resId);
    }

    public int getLoggedUserId() {
        User user = userRepo.findByEmail(loggedUserEmail());
        return user.getId();
    }

    private String loggedUserEmail() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }

}
