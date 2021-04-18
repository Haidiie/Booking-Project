package heidar.booking.service;

import heidar.booking.model.Reservation;
import heidar.booking.repo.ReservationRepo;
import heidar.booking.temp.CurrentReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final ReservationRepo reservationRepo;

    @Autowired
    public AdminService(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    public void deleteReservation(int resId) {
        reservationRepo.deleteById(resId);
    }

    public void saveAdminReservation(CurrentReservation currentReservation) {
        Reservation reservation = new Reservation();
        reservation.setUserId(currentReservation.getUserId());
        reservation.setUserEmail(currentReservation.getUserEmail());
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

    public void updateAdminReservation(CurrentReservation currentReservation) {
        Reservation reservation = new Reservation();
        reservation.setUserId(currentReservation.getUserId());
        reservation.setUserEmail(currentReservation.getUserEmail());
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


    public CurrentReservation reservationToAdminCurrentReservationById(int resId) {
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
        currentReservation.setUserEmail(reservation.getUserEmail());
        return currentReservation;
    }

    public Reservation getReservationForLoggedUserById(int resId) {
        return reservationRepo.findById(resId);
    }

}
