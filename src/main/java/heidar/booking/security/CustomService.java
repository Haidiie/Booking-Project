package heidar.booking.security;

import heidar.booking.model.User;
import heidar.booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(s);
        if (user == null){
            throw new UsernameNotFoundException("user not found!");
        }
        CustomUserDetails customUser = new CustomUserDetails(user);
        return customUser;
    }
}
