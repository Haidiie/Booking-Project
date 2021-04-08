package heidar.booking.repo;

import heidar.booking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    Reservation findById(int resId);

    Collection<Reservation> findAllByUserId(int userId);

    @Query("select p from Reservation p where p.userEmail like %:n%")
    List<Reservation> findbyhavingemail(@Param("n") String name);
}
