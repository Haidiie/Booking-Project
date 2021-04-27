package heidar.booking.repo;

import heidar.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{
    User findByEmail(String email);

    @Query("select p from User p where p.id = :n")
    User findbyId(@Param("n") Integer id);

    @Query("select p from User p where p.email like %:n%")
    List<User> findbyemail(@Param("n") String name);
}
