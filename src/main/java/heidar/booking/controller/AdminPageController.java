package heidar.booking.controller;


import heidar.booking.model.Reservation;
import heidar.booking.repo.ReservationRepo;
import heidar.booking.service.UserService;
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
    private final UserService userService;

    @Autowired
    public AdminPageController(ReservationRepo reservationRepo, UserService userService) {
        this.reservationRepo = reservationRepo;
        this.userService = userService;
    }

    public List<Reservation> allReservations;

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
        userService.updateAdminReservation(currentReservation);
        return "redirect:/admin/reservations";
    }

    @PostMapping("/proceed-reservation")
    public String proceedReservations(@Valid @ModelAttribute("newRes") CurrentReservation currentReservation) {
        userService.saveAdminReservation(currentReservation);
        return "redirect:/admin/reservations";
    }

    @PostMapping("/reservation-delete")
    public String deleteReservation(@RequestParam("resId") int resId) {
        userService.deleteReservation(resId);
        return "redirect:/admin/reservations";
    }

    @PostMapping("/reservation-update")
    public String updateReservation(@RequestParam("resId") int resId, Model model) {
        model.addAttribute("newRes", userService.reservationToAdminCurrentReservationById(resId));
        /////////////
        return "/admin/update-booking";
    }

}
