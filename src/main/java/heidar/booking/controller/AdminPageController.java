package heidar.booking.controller;


import heidar.booking.model.Reservation;
import heidar.booking.model.User;
import heidar.booking.repo.ReservationRepo;
import heidar.booking.repo.UserRepo;
import heidar.booking.service.AdminService;
import heidar.booking.temp.CurrentReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final ReservationRepo reservationRepo;
    private final UserRepo userRepo;
    private final AdminService adminService;

    @Autowired
    public AdminPageController(ReservationRepo reservationRepo, AdminService adminService, UserRepo userRepo) {
        this.reservationRepo = reservationRepo;
        this.adminService = adminService;
        this.userRepo = userRepo;
    }

    public List<Reservation> allReservations;
    public List<User> allUsers;

    @GetMapping("/main")
    public String showMainPage() {
        return "/admin/admin-main";
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    @GetMapping("/reservations")
    public String showAllRoomPage(Model model, String name) {
        if(isNullOrEmpty(name)){
            model.addAttribute("resList", reservationRepo.findAll());
        }else {
            allReservations = reservationRepo.findbyemail(name);
            model.addAttribute("resList", allReservations);
        }

        return "/admin/all-reservations";
    }

    @GetMapping("/users")
    public String showAllUsers(Model model, String name) {
        if(isNullOrEmpty(name)){
            model.addAttribute("userList", userRepo.findAll());
        }else {
            allUsers = userRepo.findbyemail(name);
            model.addAttribute("userList", allUsers);
        }

        return "/admin/all-users";
    }

    @GetMapping("/booking-rooms")
    public String newReservations(Model model) {
        model.addAttribute("newRes", new CurrentReservation());
        return "/admin/booking-rooms";
    }

    @GetMapping("/update-booking")
    public String updateReservations(Model model) {
        model.addAttribute("newRes", new CurrentReservation());
        return "/admin/update-booking";
    }

    @PostMapping("/proceed-update-reservation")
    public String proceedUpdateReservations(@Valid @ModelAttribute("newRes") CurrentReservation currentReservation) {
        adminService.updateAdminReservation(currentReservation);
        return "redirect:/admin/reservations";
    }

    @PostMapping("/proceed-reservation")
    public String proceedReservations(@Valid @ModelAttribute("newRes") CurrentReservation currentReservation) {
        adminService.saveAdminReservation(currentReservation);
        return "redirect:/admin/reservations";
    }

    @PostMapping("/reservation-delete")
    public String deleteReservation(@RequestParam("resId") int resId) {
        adminService.deleteReservation(resId);
        return "redirect:/admin/reservations";
    }

    @PostMapping("/reservation-update")
    public String updateReservation(@RequestParam("resId") int resId, Model model) {
        model.addAttribute("newRes", adminService.reservationToAdminCurrentReservationById(resId));
        /////////////
        return "/admin/update-booking";
    }

}
