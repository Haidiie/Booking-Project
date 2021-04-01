package heidar.booking.repo;


import heidar.booking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Integer> {

    Reservation findById(int resId);

    Collection<Reservation> findAllByUserId(int userId);
}
