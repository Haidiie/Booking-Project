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

    //kryptera användares lösenord
    public String enCryptedPassword(User user) {
        return encrypt.encode(user.getPassword());
    }

    //spara användare
    public void save(User user) {
        userRepo.save(user);
    }

    //identifierar inloggad användare
    private String loggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

    //hämtar user id för inloggad användare
    public int getLoggedUserId() {
        User user = userRepo.findByEmail(loggedUser());
        return user.getId();
    }

    //hämtar email för inloggad användare
    public String getLoggedUserEmail() {
        User user = userRepo.findByEmail(loggedUser());
        return user.getEmail();
    }

    //hämtar alla personliga reservationer för användare
    public Collection<Reservation> getReservationsForLoggedUser() {
        return reservationRepo.findAllByUserId((getLoggedUserId()));
    }

    //raderar reservation
    public void deleteReservation(int resId) {
        reservationRepo.deleteById(resId);
    }

    //spara eller ändra reservation
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

    //hämtar reservations id från reservations repository
    public Reservation getReservationById(int resId) {
        return reservationRepo.findById(resId);
    }

    //hämtar all sparad info för reservationen
    public CurrentReservation currentReservationById(int resId) {
        Reservation reservation = getReservationById(resId);
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

}
