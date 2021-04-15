package heidar.booking.service;

import heidar.booking.model.Reservation;
import heidar.booking.model.User;
import heidar.booking.repo.ReservationRepo;
import heidar.booking.repo.UserRepo;
import heidar.booking.temp.CurrentReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class UserService {

    private final UserRepo userRepo;
    private final ReservationRepo reservationRepo;
    private final BCryptPasswordEncoder encrypt;

    @Autowired
    public UserService(UserRepo userRepo, ReservationRepo reservationRepo, BCryptPasswordEncoder encrypt) {
        this.userRepo = userRepo;
        this.reservationRepo = reservationRepo;
        this.encrypt = encrypt;
    }

    public String enCryptedPassword(User user) {
        return encrypt.encode(user.getPassword());
    }


    public void save(User user) {
        userRepo.save(user);
    }

    public Collection<Reservation> getReservationsForLoggedUser() {
        return reservationRepo.findAllByUserId((getLoggedUserId()));
    }

    public void deleteReservation(int resId) {
        reservationRepo.deleteById(resId);
    }

    public void saveOrUpdateReservation(CurrentReservation currentReservation) {
        Reservation reservation = new Reservation();
        reservation.setUserId(getLoggedUserId());
        reservation.setUserEmail(getLoggedUserEmail());
        reservation.setArrivalDate(currentReservation.getArrivalDate());
        reservation.setDinner(currentReservation.getDinner());
        reservation.setStayDays(currentReservation.getStayDays());
        reservation.setChildren(currentReservation.getChildren());
        reservation.setPersons(currentReservation.getPersons());
        reservation.setPrice(currentReservation.getPrice());
        reservation.setRooms(currentReservation.getRooms());
        reservation.setRoomType(currentReservation.getRoomType());
        reservation.setId(currentReservation.getId());
        reservationRepo.save(reservation);
    }

    public CurrentReservation reservationToCurrentReservationById(int resId) {
        Reservation reservation = getReservationForLoggedUserById(resId);
        CurrentReservation currentReservation = new CurrentReservation();
        currentReservation.setArrivalDate(reservation.getArrivalDate());
        currentReservation.setDinner(reservation.getDinner());
        currentReservation.setStayDays(reservation.getStayDays());
        currentReservation.setChildren(reservation.getChildren());
        currentReservation.setPersons(reservation.getPersons());
        currentReservation.setUserId(reservation.getUserId());
        currentReservation.setRooms(reservation.getRooms());
        currentReservation.setPrice(reservation.getPrice());
        currentReservation.setRoomType(reservation.getRoomType());
        currentReservation.setId(reservation.getId());
        return currentReservation;
    }

    public Reservation getReservationForLoggedUserById(int resId) {
        return reservationRepo.findById(resId);
    }

    public int getLoggedUserId() {
        User user = userRepo.findByEmail(loggedUserEmail());
        return user.getId();
    }

    public String getLoggedUserEmail() {
        User user = userRepo.findByEmail(loggedUserEmail());
        return user.getEmail();
    }

    private String loggedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

}
